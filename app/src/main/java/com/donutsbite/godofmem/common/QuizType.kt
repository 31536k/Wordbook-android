package com.donutsbite.godofmem.common

enum class QuizType(val title: String, val value: Int) {
    FIND_ANSWER("답 맞추기", 0),
    FIND_ASKING("문제 맞추기", 1);

    companion object {
        fun fromValue(value: Int): QuizType = values().find { it.value == value } ?: FIND_ANSWER
    }
}