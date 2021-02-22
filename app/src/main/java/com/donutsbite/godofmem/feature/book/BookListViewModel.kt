package com.donutsbite.godofmem.feature.book

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.domain.Book
import com.donutsbite.godofmem.feature.book.BookDataSource

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
}