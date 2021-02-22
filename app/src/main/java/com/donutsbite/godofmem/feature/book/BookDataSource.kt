package com.donutsbite.godofmem.feature.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.domain.Book
import com.donutsbite.godofmem.util.ToastUtil

class BookDataSource {
    private val bookListLiveData = MutableLiveData<List<Book>>()

    fun getBookList(): LiveData<List<Book>> {
        return bookListLiveData
    }

    fun addBook(book: Book) {
        addAllBook(listOf(book))
    }

    private fun addAllBook(books: List<Book>) {
        val currentList = bookListLiveData.value
        if (currentList == null) {
            bookListLiveData.postValue(books)
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.addAll(0, books)
            bookListLiveData.postValue(updatedList)
        }
    }

    fun setBooks(books: List<Book>) {
        bookListLiveData.postValue(books)
    }

    private fun clearBooks() {
        bookListLiveData.postValue(mutableListOf())
    }

    fun requestBookList() {
        clearBooks()
        ApiLauncher.launchMain(
            { ApiService.instance.getBookList() },
            { response -> addAllBook(response.books.map{ Book.fromResponse(it)}) },
            { error -> ToastUtil.show("목록을 불러오지 못했습니다.")}
        )
    }

    companion object {
        private var instance: BookDataSource? = null

        fun getDataSource(): BookDataSource {
            return synchronized(BookDataSource::class) {
                val newInstance = instance
                    ?: BookDataSource()
                instance = newInstance
                newInstance
            }
        }
    }
}