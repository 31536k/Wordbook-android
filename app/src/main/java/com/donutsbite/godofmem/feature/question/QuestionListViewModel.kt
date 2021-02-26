package com.donutsbite.godofmem.feature.question

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.domain.Question
import com.donutsbite.godofmem.feature.question.QuestionDataSource

class QuestionListViewModel(
    private val questionDataSource: QuestionDataSource
): ViewModel() {

    val questionListLiveData = questionDataSource.getQuestionList()

    fun insertWord(question: Question) {
        questionDataSource.addQuestion(question)
    }

    fun requestQuestionsInChapter(chapterId: Long, readOnly: Boolean) {
        questionDataSource.requestQuestionsInChapter(chapterId, readOnly)
    }

    fun getCurrentQuestionList(): List<Question>? {
        return questionDataSource.getCurrentQuestionList()
    }
}