package com.donutsbite.godofmem.feature.book.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.feature.book.data.Book

class BookListAdapter(
    private val onClick: (Book) -> Unit
): ListAdapter<Book, BookListAdapter.BookViewHolder>(BookDiffCallback) {

    class BookViewHolder(itemView: View, val onClick: (Book) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private var currentBook: Book? = null

        init {
            itemView.setOnClickListener{
                currentBook?.let {
                    onClick(it)
                }
            }
        }

        fun bind(book: Book) {
            currentBook = book
            titleView.text = book.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }
}

object BookDiffCallback: DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }
}