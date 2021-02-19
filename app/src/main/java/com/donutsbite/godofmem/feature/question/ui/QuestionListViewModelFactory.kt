package com.donutsbite.godofmem.feature.question.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.donutsbite.godofmem.feature.question.data.QuestionDataSource

class QuestionListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionListViewModel(QuestionDataSource.getDataSource()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}