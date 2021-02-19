package com.donutsbite.godofmem.feature.question.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.feature.question.data.Question

class QuestionListAdapter(
    private val onClick: (Question) -> Unit
): ListAdapter<Question, QuestionListAdapter.QuestionViewHolder>(QuestionDiffCallback) {

    class QuestionViewHolder(itemView: View, val onClick: (Question) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val askingTextView: TextView = itemView.findViewById(R.id.asking)
        private val answerTextView: TextView = itemView.findViewById(R.id.answer)
        private var currentChapter: Question? = null

        init {
            itemView.setOnClickListener{
                currentChapter?.let {
                    onClick(it)
                }
            }
        }

        fun bind(Question: Question) {
            currentChapter = Question
            askingTextView.text = Question.asking
            answerTextView.text = Question.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return QuestionViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = getItem(position)
        holder.bind(question)
    }
}

object QuestionDiffCallback: DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.id == newItem.id
    }
}