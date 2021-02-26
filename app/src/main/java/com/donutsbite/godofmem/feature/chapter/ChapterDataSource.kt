package com.donutsbite.godofmem.feature.chapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.domain.Book
import com.donutsbite.godofmem.domain.Chapter
import com.donutsbite.godofmem.util.ToastUtil

class ChapterDataSource {
    private val chapterListLiveData = MutableLiveData<List<Chapter>>()

    fun getChapterList(): LiveData<List<Chapter>> {
        return chapterListLiveData
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

    fun setChapters(chapters: List<Chapter>) {
        chapterListLiveData.postValue(chapters)
    }

    private fun clearChapters() {
        chapterListLiveData.postValue(mutableListOf())
    }


    fun requestAllBooksAndChaptersOfUser() {
        ApiLauncher.launchMain(
            { ApiService.instance.getChaptersOfUser() },
            { response -> addAllChapter(response.chapters.map{ Chapter.fromResponse(it)}) },
            { error -> ToastUtil.show("목록을 불러오지 못했습니다. 1")}
        )
    }

    companion object {
        private var instance: ChapterDataSource? = null

        fun getDataSource(): ChapterDataSource {
            return synchronized(ChapterDataSource::class) {
                val newInstance = instance
                    ?: ChapterDataSource()
                instance = newInstance
                newInstance
            }
        }
    }
}