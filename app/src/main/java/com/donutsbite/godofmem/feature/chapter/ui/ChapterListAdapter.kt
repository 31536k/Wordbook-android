package com.donutsbite.godofmem.feature.chapter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.feature.chapter.data.Chapter

class ChapterListAdapter(
    private val onClick: (Chapter) -> Unit
): ListAdapter<Chapter, ChapterListAdapter.ChapterViewHolder>(ChapterDiffCallback) {

    class ChapterViewHolder(itemView: View, val onClick: (Chapter) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private var currentChapter: Chapter? = null

        init {
            itemView.setOnClickListener{
                currentChapter?.let {
                    onClick(it)
                }
            }
        }

        fun bind(chapter: Chapter) {
            currentChapter = chapter
            titleView.text = chapter.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chapter_item, parent, false)
        return ChapterViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = getItem(position)
        holder.bind(chapter)
    }
}

object ChapterDiffCallback: DiffUtil.ItemCallback<Chapter>() {
    override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem.id == newItem.id
    }
}