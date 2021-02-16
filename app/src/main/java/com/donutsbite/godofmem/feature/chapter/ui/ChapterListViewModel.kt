package com.donutsbite.godofmem.feature.chapter.ui

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.feature.chapter.data.Chapter
import com.donutsbite.godofmem.feature.chapter.data.ChapterDataSource

class ChapterListViewModel(
    private val chapterDataSource: ChapterDataSource
): ViewModel() {

    val chapterListLiveData = chapterDataSource.getChapterList()

    fun insertBook(chapter: Chapter) {
        chapterDataSource.addChapter(chapter)
    }

    fun requestChaptersOfBook(bookId: Int) {
        chapterDataSource.requestChaptersOfBook(bookId)
    }

    fun reloadChaptersOfBook(bookId: Int) {
        chapterDataSource.requestChaptersOfBook(bookId)
    }
}