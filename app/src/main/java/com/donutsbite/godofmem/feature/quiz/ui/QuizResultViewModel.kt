package com.donutsbite.godofmem.feature.quiz.ui

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.feature.quiz.data.QuizResultDataSource

class QuizResultViewModel(
    private val quizResultDataSource: QuizResultDataSource
): ViewModel() {

    val quizResultLiveData = quizResultDataSource.getQuizResult()

    fun requestQuizResult(chapterId: Long) {
        quizResultDataSource.requestQuizResult(chapterId)
    }
}