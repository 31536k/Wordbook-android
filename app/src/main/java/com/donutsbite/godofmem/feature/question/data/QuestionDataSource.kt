package com.donutsbite.godofmem.feature.question.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.donutsbite.godofmem.api.ApiService
import com.donutsbite.godofmem.api.module.ApiLauncher
import com.donutsbite.godofmem.util.ToastUtil

class QuestionDataSource {
    private val questionListLiveData = MutableLiveData<List<Question>>()

    fun getWordList(): LiveData<List<Question>> {
        return questionListLiveData
    }

    fun addWord(question: Question) {

    }

    fun addAllWord(questions: List<Question>) {
        val currentList = questionListLiveData.value
        if (currentList == null) {
            questionListLiveData.postValue(questions)
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.addAll(0, questions)
            questionListLiveData.postValue(updatedList)
        }
    }

    fun setWords(questions: List<Question>) {
        questionListLiveData.postValue(questions)
    }

    private fun clearWords() {
        questionListLiveData.postValue(mutableListOf())
    }

    fun requestWordsInChapter(chapterId: Long) {
        clearWords()
        ApiLauncher.launchMain(
            { ApiService.instance.getQuestionsInChapter(chapterId) },
            { response -> addAllWord(response.questions.map{ Question.fromResponse(it)}) },
            { error -> ToastUtil.show("목록을 불러오지 못했습니다.")}
        )
    }

    companion object {
        private var instance: QuestionDataSource? = null

        fun getDataSource(): QuestionDataSource {
            return synchronized(QuestionDataSource::class) {
                val newInstance = instance ?: QuestionDataSource()
                instance = newInstance
                newInstance
            }
        }
    }
}