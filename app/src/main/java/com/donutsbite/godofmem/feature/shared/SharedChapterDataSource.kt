package com.donutsbite.godofmem.feature.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.domain.Chapter
import com.donutsbite.godofmem.util.ToastUtil

class SharedChapterDataSource {
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

    fun clearChapters() {
        chapterListLiveData.postValue(mutableListOf())
    }


    fun requestSharedChapters() {
        ApiLauncher.launchMain(
            { ApiService.instance.getSharedChapters() },
            { response -> addAllChapter(response.chapters.map{ Chapter.fromResponse(it)}) },
            { error -> ToastUtil.show("목록을 불러오지 못했습니다. 1")}
        )
    }

    companion object {
        private var instance: SharedChapterDataSource? = null

        fun getDataSource(): SharedChapterDataSource {
            return synchronized(SharedChapterDataSource::class) {
                val newInstance = instance
                    ?: SharedChapterDataSource()
                instance = newInstance
                newInstance
            }
        }
    }
}