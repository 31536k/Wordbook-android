package com.donutsbite.godofmem.feature.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.donutsbite.godofmem.feature.chapter.ChapterDataSource
import com.donutsbite.godofmem.feature.chapter.ChapterListViewModel
import java.lang.IllegalArgumentException

class SharedChapterListViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedChapterListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedChapterListViewModel(
                SharedChapterDataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}