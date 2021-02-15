package com.donutsbite.godofmem.feature.book.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.donutsbite.godofmem.feature.book.data.BookDataSource

class BookListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookListViewModel(BookDataSource.getDataSource()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}