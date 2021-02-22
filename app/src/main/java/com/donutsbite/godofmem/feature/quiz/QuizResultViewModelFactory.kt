package com.donutsbite.godofmem.feature.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuizResultViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizResultViewModel(
                QuizResultDataSource.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}