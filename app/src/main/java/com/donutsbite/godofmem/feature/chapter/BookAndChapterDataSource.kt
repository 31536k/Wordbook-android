package com.donutsbite.godofmem.feature.chapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.domain.Book
import com.donutsbite.godofmem.domain.Chapter
import com.donutsbite.godofmem.util.ToastUtil

class BookAndChapterDataSource {
    private val chapterListLiveData = MutableLiveData<List<Chapter>>()
    private val bookListLiveData = MutableLiveData<List<Book>>()

    fun getChapterList(): LiveData<List<Chapter>> {
        return chapterListLiveData
    }

    fun getBookList(): LiveData<List<Book>> {
        return bookListLiveData
    }

    fun addChapter(chapter: Chapter) {
        addAllChapter(listOf(chapter))
    }

    private fun addAllChapter(chapters: List<Chapter>) {
        val currentList = chapterListLiveData.value
        if (currentList == null) {
            chapterListLiveData.postValue(chapters)
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.addAll(0, chapters)
            chapterListLiveData.postValue(updatedList)
        }
    }

    private fun addAllBook(books: List<Book>) {
        val currentList = bookListLiveData.value
        if (currentList == null) {
            bookListLiveData.postValue(books)
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.addAll(0, books)
            bookListLiveData.postValue(updatedList)
        }
    }

    fun setChapters(chapters: List<Chapter>) {
        chapterListLiveData.postValue(chapters)
    }

    private fun clearChapters() {
        chapterListLiveData.postValue(mutableListOf())
    }

    private fun clearBooks() {
        bookListLiveData.postValue(mutableListOf())
    }

    fun requestAllBooksAndChaptersOfUser() {
        clearChapters()
        ApiLauncher.launchMain(
            { ApiService.instance.getChaptersOfUser() },
            { response -> addAllChapter(response.chapters.map{ Chapter.fromResponse(it)}) },
            { error -> ToastUtil.show("목록을 불러오지 못했습니다. 1")}
        )

        clearBooks()
        ApiLauncher.launchMain(
            { ApiService.instance.getBookList() },
            { response -> addAllBook(response.books.map{ Book.fromResponse(it)}) },
            { error -> ToastUtil.show("목록을 불러오지 못했습니다. 2")}
        )
    }

    companion object {
        private var instance: BookAndChapterDataSource? = null

        fun getDataSource(): BookAndChapterDataSource {
            return synchronized(BookAndChapterDataSource::class) {
                val newInstance = instance
                    ?: BookAndChapterDataSource()
                instance = newInstance
                newInstance
            }
        }
    }
}