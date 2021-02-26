package com.donutsbite.godofmem.feature.shared

import androidx.lifecycle.ViewModel

class SharedChapterListViewModel(
    private val sharedChapterDataSource: SharedChapterDataSource
): ViewModel() {
    val sharedChapterListLiveData = sharedChapterDataSource.getChapterList()

    fun requestSharedChapters() {
        sharedChapterDataSource.requestSharedChapters()
    }

    override fun onCleared() {
        sharedChapterDataSource.clearChapters()
        super.onCleared()
    }
}