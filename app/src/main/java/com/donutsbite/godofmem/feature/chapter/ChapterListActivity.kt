package com.donutsbite.godofmem.feature.chapter

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.domain.Book
import com.donutsbite.godofmem.domain.Chapter
import com.donutsbite.godofmem.feature.book.BookListAdapter
import com.donutsbite.godofmem.feature.question.QuestionListActivity
import com.donutsbite.godofmem.util.StringStore
import com.donutsbite.godofmem.util.ToastUtil

class ChapterListActivity: AppCompatActivity() {

    private var bookId: Long = 0
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbarTitleView: TextView
    private lateinit var chapterListAdapter: ChapterListAdapter

    private val bookAndChapterListViewModel by viewModels<BookAndChapterListViewModel> {
        BookAndChapterListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapterlist)

        bookId = intent.getLongExtra(StringStore.bookId, 0)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)

        drawer = findViewById(R.id.drawer_layout)
        toolbarTitleView = findViewById(R.id.toolbar_title)
        toolbarTitleView.setOnClickListener{ titleClicked() }

        chapterListAdapter = ChapterListAdapter {
                chapterAdapterOnClick(it)
            }

        val chapterRecyclerview: RecyclerView = findViewById(R.id.chapter_recycler_view)
        chapterRecyclerview.adapter = chapterListAdapter

        bookAndChapterListViewModel.chapterListLiveData.observe(this, Observer {
            it?.let {
                chapterListAdapter.submitList(it as MutableList<Chapter>)
            }
        })

        val bookListAdapter = BookListAdapter {
            bookAdapterOnClick(it)
        }

        val bookRecyclerview: RecyclerView = findViewById(R.id.book_recycler_view)
        bookRecyclerview.adapter = bookListAdapter

        bookAndChapterListViewModel.bookListLivaData.observe(this, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    val listForFilter = it as MutableList<Book>
                    listForFilter.add(0, Book(-1, 0, "모든 노트북", null))
                    bookListAdapter.submitList(listForFilter)
                }
            }
        })

        val fab: View = findViewById(R.id.addchapter_fab)
        fab.setOnClickListener{
            fabOnClick()
        }

        bookAndChapterListViewModel.requestAllBooksAndChaptersOfUser()
    }

    private fun titleClicked() {
        drawer.openDrawer(GravityCompat.START)
    }

    private fun chapterAdapterOnClick(chapter: Chapter) {
        val intent = Intent(this, QuestionListActivity::class.java)
        intent.putExtra(StringStore.chapterId, chapter.id)
        startActivity(intent)
    }

    private fun bookAdapterOnClick(book: Book) {
        drawer.closeDrawers()
        toolbarTitleView.text = book.title

        val chapterList = bookAndChapterListViewModel.chapterListLiveData.value
        if (book.id == -1L) {
            chapterListAdapter.submitList(chapterList)
        } else {
            chapterListAdapter.submitList(chapterList?.filter { it.bookId == book.id })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_chapter_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START)
        }
        else if (item.itemId == R.id.reload) {
            bookAndChapterListViewModel.requestAllBooksAndChaptersOfUser()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun fabOnClick() {

    }
}