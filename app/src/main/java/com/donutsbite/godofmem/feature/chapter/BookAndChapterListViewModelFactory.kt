package com.donutsbite.godofmem.feature.chapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BookAndChapterListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookAndChapterListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookAndChapterListViewModel(
                BookAndChapterDataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}