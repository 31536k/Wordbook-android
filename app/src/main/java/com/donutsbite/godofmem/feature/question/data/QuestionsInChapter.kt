package com.donutsbite.godofmem.feature.question.data

import android.os.Parcel
import android.os.Parcelable

/**
 * 안쓰고 있는데 일단 남겨두자
 */
data class QuestionsInChapter(
    val chapterId: Long,
    val questionList: List<Question>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.createTypedArrayList(Question)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(chapterId)
        parcel.writeTypedList(questionList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionsInChapter> {
        override fun createFromParcel(parcel: Parcel): QuestionsInChapter {
            return QuestionsInChapter(parcel)
        }

        override fun newArray(size: Int): Array<QuestionsInChapter?> {
            return arrayOfNulls(size)
        }
    }
}