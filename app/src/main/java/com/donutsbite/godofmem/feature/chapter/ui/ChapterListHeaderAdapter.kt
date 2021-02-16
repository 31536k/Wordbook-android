package com.donutsbite.godofmem.feature.chapter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R

class ChapterListHeaderAdapter: RecyclerView.Adapter<ChapterListHeaderAdapter.HeaderViewHolder>() {
    private var chapterCount: Int = 0

    /* ViewHolder for displaying header. */
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val countTextView: TextView = itemView.findViewById(R.id.chapter_count)

        fun bind(count: Int) {
            countTextView.text = count.toString()
        }
    }

    /* Inflates view and returns HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chapterlist_header_item, parent, false)
        return HeaderViewHolder(view)
    }

    /* Binds number of flowers to the header. */
    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(chapterCount)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int {
        return 1
    }

    /* Updates header to display number of flowers when a flower is added or subtracted. */
    fun updateBookCount(updatedCount: Int) {
        chapterCount = updatedCount
        notifyDataSetChanged()
    }
}