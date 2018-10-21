package com.kllama.sunmoon.core.platform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<in ITEM>(context: Context, parent: ViewGroup?, @LayoutRes layoutRes: Int)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(layoutRes, parent, false)) {

    fun onProgressViewHolder() {
    }

    fun onBindViewHolder(item: ITEM?) {
        try {
            item?.let {
                onViewCreated(item)
            } ?: throw Exception("item must not be null data.")
        } catch (e: Exception) {
            itemView.visibility = View.GONE
        }
    }

    open fun onUpdateViewHolder(item: ITEM?) {}

    abstract fun onViewCreated(item: ITEM)

    fun setOnClickListener(onClick: (Int) -> Unit) {
        itemView.setOnClickListener{
            onClick(adapterPosition)
        }
    }

    fun setOnLongClickListener(onLongClick: (Int) -> Boolean) {
        itemView.setOnLongClickListener {
            onLongClick(adapterPosition)
        }
    }

    fun setOnChildViewClickListener(@IdRes id: Int, onClick: (Int) -> Unit) {
        findViewById<View>(id)?.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    private fun <T : View> findViewById(@IdRes id: Int): T? = itemView.findViewById(id)

    fun setText(@IdRes id: Int, text: String) {
        findViewById<TextView>(id)?.text = text
    }

    fun setImageResource(@IdRes id: Int, @DrawableRes drawableResourceId: Int) {
        findViewById<ImageView>(id)?.setImageResource(drawableResourceId)
    }
}
