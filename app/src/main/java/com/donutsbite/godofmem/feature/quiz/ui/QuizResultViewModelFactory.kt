package com.donutsbite.godofmem.feature.quiz.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.donutsbite.godofmem.feature.question.data.QuestionDataSource
import com.donutsbite.godofmem.feature.question.ui.QuestionListViewModel
import com.donutsbite.godofmem.feature.quiz.data.QuizResultDataSource

class QuizResultViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizResultViewModel(QuizResultDataSource.getDataSource()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}