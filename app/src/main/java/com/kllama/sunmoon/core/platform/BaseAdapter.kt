package com.kllama.sunmoon.core.platform

import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    val items: MutableList<T> = mutableListOf()

    var onClickListener: ((Int) -> Unit)? = null

    fun onClick(listener: (Int) -> Unit) {
        onClickListener = listener
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(@IntRange(from = 0) position: Int): T = this.items[position]

    fun addItem(item: T) {
        this.items.add(item)
        notifyItemInserted(itemCount)
    }

    fun addItem(@IntRange(from = 0) position: Int, item: T) {
        this.items.add(position, item)
        notifyItemInserted(position)
    }

    fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyItemRangeInserted(itemCount, items.size)
    }

    fun addItems(@IntRange(from = 0) position: Int, items: List<T>) {
        this.items.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
    }

    fun replaceItem(@IntRange(from = 0) position: Int, item: T) {
        this.items[position] = item
        notifyItemChanged(position)
    }

    fun replaceItem(oldItem: T, newItem: T) {
        val index = this.items.indexOf(oldItem)
        replaceItem(index, newItem)
    }

    fun removeItem(@IntRange(from = 0) position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun newItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}