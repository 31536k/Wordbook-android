package com.donutsbite.godofmem.feature.book.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.feature.book.data.Book
import com.donutsbite.godofmem.feature.chapter.ui.ChapterListActivity
import com.donutsbite.godofmem.util.StringStore
import com.donutsbite.godofmem.util.ToastUtil

class BookListActivity: AppCompatActivity() {

    private val bookListViewModel by viewModels<BookListViewModel> {
        BookListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booklist)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val headerAdapter = BookListHeaderAdapter()
        val bookListAdapter = BookListAdapter{adapterOnClick(it)}
        val concatAdapter = ConcatAdapter(headerAdapter, bookListAdapter)

        val recyclerview: RecyclerView = findViewById(R.id.recycler_view)
        recyclerview.adapter = concatAdapter

        bookListViewModel.bookListLiveData.observe(this, Observer {
            it?.let {
                bookListAdapter.submitList(it as MutableList<Book>)
                headerAdapter.updateBookCount(it.size)
            }
        })

        val fab: View = findViewById(R.id.addbook_fab)
        fab.setOnClickListener{
            fabOnClick()
        }

        bookListViewModel.requestBookList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.reload) {
            ToastUtil.show("Reload")
            bookListViewModel.reloadBookList()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun adapterOnClick(book: Book) {
        val intent = Intent(this, ChapterListActivity::class.java)
        intent.putExtra(StringStore.bookId, book.id)
        startActivity(intent)
    }

    private fun fabOnClick() {
//        val intent = Intent(this, ChapterListActivity::class.java)
//        startActivity(intent)
    }
}