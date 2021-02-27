package com.donutsbite.godofmem.feature.public

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class PublicChapterListViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PublicChapterListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PublicChapterListViewModel(
                PublicChapterDataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}