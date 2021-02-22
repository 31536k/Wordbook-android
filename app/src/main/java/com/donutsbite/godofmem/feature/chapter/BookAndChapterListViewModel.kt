package com.donutsbite.godofmem.feature.chapter

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.domain.Chapter

class BookAndChapterListViewModel(
    private val bookAndChapterDataSource: BookAndChapterDataSource
): ViewModel() {

    val chapterListLiveData = bookAndChapterDataSource.getChapterList()

    val bookListLivaData = bookAndChapterDataSource.getBookList()

    fun insertChapter(chapter: Chapter) {
        bookAndChapterDataSource.addChapter(chapter)
    }

    fun requestAllBooksAndChaptersOfUser() {
        bookAndChapterDataSource.requestAllBooksAndChaptersOfUser()
    }
}