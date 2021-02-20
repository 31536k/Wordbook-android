package com.donutsbite.godofmem.feature.question.ui

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.feature.question.data.Question
import com.donutsbite.godofmem.feature.question.data.QuestionDataSource

class QuestionListViewModel(
    private val questionDataSource: QuestionDataSource
): ViewModel() {

    val questionListLiveData = questionDataSource.getQuestionList()

    fun insertWord(question: Question) {
        questionDataSource.addQuestion(question)
    }

    fun requestWordsInChapter(chapterId: Long) {
        questionDataSource.requestQuestionsInChapter(chapterId)
    }

    fun getCurrentQuestionList(): List<Question>? {
        return questionDataSource.getCurrentQuestionList()
    }
}