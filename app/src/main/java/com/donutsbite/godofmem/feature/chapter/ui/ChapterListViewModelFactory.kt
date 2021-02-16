package com.donutsbite.godofmem.feature.chapter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.donutsbite.godofmem.feature.chapter.data.ChapterDataSource
import java.lang.IllegalArgumentException

class ChapterListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChapterListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChapterListViewModel(ChapterDataSource.getDataSource()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}