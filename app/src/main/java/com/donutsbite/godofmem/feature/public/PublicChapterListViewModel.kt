package com.donutsbite.godofmem.feature.public

import androidx.lifecycle.ViewModel

class PublicChapterListViewModel(
    private val publicChapterDataSource: PublicChapterDataSource
): ViewModel() {
    val publicChapterListLiveData = publicChapterDataSource.getChapterList()

    fun requestPublicChapters() {
        publicChapterDataSource.requestPublicChapters()
    }

    override fun onCleared() {
        publicChapterDataSource.clearChapters()
        super.onCleared()
    }
}