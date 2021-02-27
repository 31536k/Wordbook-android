package com.donutsbite.godofmem.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.domain.Book
import com.donutsbite.godofmem.feature.book.BookListAdapter
import com.donutsbite.godofmem.feature.book.BookListViewModel
import com.donutsbite.godofmem.feature.book.BookListViewModelFactory
import com.donutsbite.godofmem.feature.chapter.ChapterListFragment
import com.donutsbite.godofmem.feature.profile.ProfileFragment
import com.donutsbite.godofmem.feature.public.PublicChapterListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {
    private val fragments = mutableListOf<Fragment>()
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toolbarTitleView: TextView
    private val titleList = listOf("모든 노트", "모두의 노트북", "프로필")
    private var currentFragment: Fragment? = null
    private lateinit var drawer: DrawerLayout

    private val myBookListViewModel by viewModels<BookListViewModel> {
        BookListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragments.add(ChapterListFragment())
        fragments.add(PublicChapterListFragment())
        fragments.add(ProfileFragment())

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.private_note->setCurrentFragment(0)
                R.id.public_note->setCurrentFragment(1)
                R.id.profile->setCurrentFragment(2)
            }
            true
        }

        drawer = findViewById(R.id.drawer_layout)

        setupToolbar()

        setCurrentFragment(0)

        setupBookList()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)

        toolbarTitleView = findViewById(R.id.toolbar_title)
        toolbarTitleView.setOnClickListener{ openDrawer() }
    }

    private fun openDrawer() {
        if (currentFragment is ChapterListFragment) {
            drawer.openDrawer(GravityCompat.START)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chapter_list, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            openDrawer()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(index: Int) {
        val fragment = fragments[index]
        supportFragmentManager.present(fragment, currentFragment, R.id.fragment)
        currentFragment = fragment
//        bottomNavigationView.menu.getItem(index).isChecked = true
//        supportFragmentManager.beginTransaction().apply {
//            add(R.id.fragment, fragment)
//            commit()
//        }

        toolbarTitleView.text = titleList[index]

        if (fragment is ChapterListFragment) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setupBookList() {
        val bookListAdapter = BookListAdapter {
            bookAdapterOnClick(it)
        }

        val bookRecyclerview: RecyclerView = findViewById(R.id.book_recycler_view)
        bookRecyclerview.adapter = bookListAdapter

        myBookListViewModel.bookListLiveData.observe(this, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    val listForFilter = it as MutableList<Book>
                    listForFilter.add(0, Book(-1, 0, "모든 노트", null, true))
                    bookListAdapter.submitList(listForFilter)
                }
            }
        })

        myBookListViewModel.requestBookList()
    }

    private fun bookAdapterOnClick(book: Book) {
        drawer.closeDrawers()

        if (currentFragment is ChapterListFragment) {
            toolbarTitleView.text = book.title
            (currentFragment as ChapterListFragment).filterWithBook(book)
        }
    }
}

fun FragmentManager.present(newFragment: Fragment, lastFragment: Fragment? = null, containerId: Int) {
    if (lastFragment == newFragment) return

    val transaction = beginTransaction()
    if (lastFragment != null && findFragmentByTag(lastFragment.getUniqueTag()) != null) {
        transaction.hide(lastFragment)
    }

    val existingFragment = findFragmentByTag(newFragment.getUniqueTag())
    if (existingFragment != null) {
        transaction.show(existingFragment).commit()
    } else {
        transaction.add(containerId, newFragment, newFragment.getUniqueTag()).commit()
    }
}

fun Fragment.getUniqueTag(): String = this::class.java.simpleName