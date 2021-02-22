package com.donutsbite.godofmem.feature.quiz

import androidx.lifecycle.ViewModel
import com.donutsbite.godofmem.feature.quiz.QuizResultDataSource

class QuizResultViewModel(
    private val quizResultDataSource: QuizResultDataSource
): ViewModel() {

    val quizResultLiveData = quizResultDataSource.getQuizResult()

    fun requestQuizResult(chapterId: Long) {
        quizResultDataSource.requestQuizResult(chapterId)
    }
}