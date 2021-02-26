package com.donutsbite.godofmem.feature.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.donutsbite.godofmem.R
import com.donutsbite.godofmem.domain.Book

class BookListAdapter(
    private val onClick: (Book) -> Unit
): ListAdapter<Book, BookListAdapter.BookViewHolder>(
    BookDiffCallback
) {

    class BookViewHolder(itemView: View, val viewHolderOnClick: (Book) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private var currentBook: Book? = null

        init {
            itemView.setOnClickListener{
                currentBook?.let {
                    viewHolderOnClick(it)
                }
            }
        }

        fun bind(book: Book) {
            currentBook = book
            titleView.text = book.title
            if (book.selected) {
                titleView.setTextColor(itemView.resources.getColor(R.color.red, null))
            } else {
                titleView.setTextColor(itemView.resources.getColor(R.color.black, null))
            }
        }
    }

    private fun onViewHolderClick(book: Book) {
        for (i in 0 until itemCount) {
            if (getItem(i).selected) {
                notifyItemChanged(i)
            }

            getItem(i).selected = (getItem(i) == book)
            if (getItem(i).selected) {
                notifyItemChanged(i)
            }
        }

        onClick(book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(
            view,
            ::onViewHolderClick
        )
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