package com.donutsbite.godofmem.feature.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R

class QuestionListHeaderAdapter: RecyclerView.Adapter<QuestionListHeaderAdapter.HeaderViewHolder>() {
    private var questionCount: Int = 0

    /* ViewHolder for displaying header. */
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val countTextView: TextView = itemView.findViewById(R.id.question_count)

        fun bind(count: Int) {
            countTextView.text = count.toString() + "개의 문제"
        }
    }

    /* Inflates view and returns HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.questionlist_header_item, parent, false)
        return HeaderViewHolder(
            view
        )
    }

    /* Binds number of flowers to the header. */
    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(questionCount)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int {
        return 1
    }

    /* Updates header to display number of flowers when a flower is added or subtracted. */
    fun updateQuestionCount(updatedCount: Int) {
        questionCount = updatedCount
        notifyDataSetChanged()
    }
}