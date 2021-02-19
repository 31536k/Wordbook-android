package com.donutsbite.godofmem.feature.question.ui

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.feature.question.data.Question
import com.donutsbite.godofmem.feature.question.data.QuestionDataSource

class QuestionListViewModel(
    private val questionDataSource: QuestionDataSource
): ViewModel() {

    val questionListLiveData = questionDataSource.getWordList()

    fun insertWord(question: Question) {
        questionDataSource.addWord(question)
    }

    fun requestWordsInChapter(chapterId: Long) {
        questionDataSource.requestWordsInChapter(chapterId)
    }
}