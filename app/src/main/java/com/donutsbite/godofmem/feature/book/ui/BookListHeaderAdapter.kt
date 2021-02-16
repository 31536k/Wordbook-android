package com.donutsbite.godofmem.feature.book.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R

class BookListHeaderAdapter: RecyclerView.Adapter<BookListHeaderAdapter.HeaderViewHolder>() {
    private var bookCount: Int = 0

    /* ViewHolder for displaying header. */
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val bookCountTextView: TextView = itemView.findViewById(R.id.book_count)

        fun bind(count: Int) {
            bookCountTextView.text = count.toString()
        }
    }

    /* Inflates view and returns HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booklist_header_item, parent, false)
        return HeaderViewHolder(view)
    }

    /* Binds number of flowers to the header. */
    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(bookCount)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int {
        return 1
    }

    /* Updates header to display number of flowers when a flower is added or subtracted. */
    fun updateBookCount(updatedCount: Int) {
        bookCount = updatedCount
        notifyDataSetChanged()
    }
}