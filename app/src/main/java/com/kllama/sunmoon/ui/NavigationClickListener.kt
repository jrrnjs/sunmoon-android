package com.kllama.sunmoon.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Interpolator
import android.widget.ImageView
import com.kllama.sunmoon.R

/**
 * [android.view.View.OnClickListener] used to translate the product grid sheet downward on
 * the Y-axis when the navigation icon in the toolbar is pressed.
 */
class NavigationClickListener @JvmOverloads internal constructor(
        private val context: Context, private val sheet: View, private val interpolator: Interpolator? = null,
        private val openIcon: Drawable? = null, private val closeIcon: Drawable? = null) : View.OnClickListener {

    private val animatorSet = AnimatorSet()
    private val height: Int
    private var backdropShown = false
    private lateinit var iconImageView: ImageView

    init {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
    }

    override fun onClick(view: View) {
        if (!::iconImageView.isInitialized) {
            if (view !is ImageView) {
                throw IllegalArgumentException("updateIcon() must be called on an ImageView")
            }
            iconImageView = view
        }

        if (backdropShown) {
            menuClose()
        } else {
            menuOpen()
        }
    }

    private fun menuOpen() {
        startAnimation((height - context.resources.getDimensionPixelSize(R.dimen.main_reveal_height)).toFloat())
    }

    fun menuClose() {
        startAnimation(0f)
    }

    private fun startAnimation(y: Float) {
        backdropShown = !backdropShown

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        if (::iconImageView.isInitialized) {
            updateIcon()
        }

        val animator = ObjectAnimator.ofFloat(sheet, "translationY", y)
        animator.duration = 500
        if (interpolator != null) {
            animator.interpolator = interpolator
        }
        animatorSet.play(animator)
        animator.start()
    }


    private fun updateIcon() {
        if (openIcon != null && closeIcon != null) {
            if (backdropShown) {
                iconImageView.setImageDrawable(closeIcon)
            } else {
                iconImageView.setImageDrawable(openIcon)
            }
        }
    }
}
