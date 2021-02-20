package com.donutsbite.godofmem.feature.question.data

import android.os.Parcel
import android.os.Parcelable
import com.donutsbite.godofmem.api.response.QuestionResponse

data class Question(
    val id: Long,
    val asking: String,
    val answer: String,
    val wrongCount: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(asking)
        parcel.writeString(answer)
        parcel.writeInt(wrongCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {

        fun fromResponse(questionResponse: QuestionResponse) =
            Question(
                questionResponse.id,
                questionResponse.asking,
                questionResponse.answer,
                questionResponse.wrongCount
            )

        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}