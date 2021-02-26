package com.donutsbite.godofmem.feature.chapter

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.domain.Chapter

class ChapterListViewModel(
    private val chapterDataSource: ChapterDataSource
): ViewModel() {

    val chapterListLiveData = chapterDataSource.getChapterList()

    fun insertChapter(chapter: Chapter) {
        chapterDataSource.addChapter(chapter)
    }

    fun requestChaptersOfUser() {
        chapterDataSource.requestAllBooksAndChaptersOfUser()
    }
}