package com.donutsbite.godofmem.feature.book.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.feature.book.data.Book
import com.donutsbite.godofmem.util.ToastUtil

const val BOOK_ID = "book_id"

class BookListActivity: AppCompatActivity() {

    private val bookListViewModel by viewModels<BookListViewModel> {
        BookListViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booklist)

        val headerAdapter = BookListHeaderAdapter()
        val bookListAdapter = BookListAdapter{adapterOnClick(it)}
        val concatAdapter = ConcatAdapter(headerAdapter, bookListAdapter)

        val recyclerview: RecyclerView = findViewById(R.id.booklist_recycler_view)
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

    private fun adapterOnClick(book: Book) {
        ToastUtil.show("Book Title - ${book.title}")
    }

    private fun fabOnClick() {
//        val intent = Intent(this, ChapterListActivity::class.java)
//        startActivity(intent)
    }
}