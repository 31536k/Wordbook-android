package com.donutsbite.godofmem.feature.book.ui

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.feature.book.data.Book
import com.donutsbite.godofmem.feature.book.data.BookDataSource

class BookListViewModel(
    private val bookDataSource: BookDataSource
): ViewModel() {

    val bookListLiveData = bookDataSource.getBookList()

    fun insertBook(book: Book) {
        bookDataSource.addBook(book)
    }

    fun requestBookList() {
        bookDataSource.requestBookList()
    }

    fun reloadBookList() {
        bookDataSource.requestBookList()
    }
}