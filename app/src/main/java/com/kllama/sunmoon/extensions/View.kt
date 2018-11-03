package com.kllama.sunmoon.extensions

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat


infix fun View.onClick(body: () -> Unit) {
    this.setOnClickListener { body() }
}

infix fun View.onClick(body: (View) -> Unit) {
    this.setOnClickListener { body(it) }
}

infix fun Toolbar.onNavigationClick(body: () -> Unit) {
    this.setNavigationOnClickListener { body() }
}

fun View.visible(value: Boolean) {
    visibility = if (value) View.VISIBLE else View.GONE
}



//menu
fun MenuItem.setIconTint(color: Int) {
    icon?.let {
        val wrapped = DrawableCompat.wrap(it)
        it.mutate()
        DrawableCompat.setTint(wrapped, color)
        setIcon(it)
    }
}

fun MenuItem.setIconTint(context: Context, @ColorRes colorResId: Int) {
    val color = ContextCompat.getColor(context, colorResId)
    setIconTint(color)
}

fun Menu.setAllIconsTint(color: Int) {
    for (i in 0 until size()) {
        val item = getItem(i)
        item.setIconTint(color)
    }
}

fun Menu.setAllIconsTint(context: Context, @ColorRes colorResId: Int) {
    val color = ContextCompat.getColor(context, colorResId)
    setAllIconsTint(color)
}