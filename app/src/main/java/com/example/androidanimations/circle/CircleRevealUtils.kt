package com.example.androidanimations.circle

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_reveal.*
import kotlin.math.hypot

fun circularRevealView(view: View, x: Int, y: Int, show: Boolean, doOnHidden: (()-> Unit)? = null) {
    /*val cx = view.right
    val cy = view.bottom*/
    val finalRadius = hypot(view.width.toDouble(), view.height.toDouble()).toFloat()

    val circularReveal =
        if (show) {
            view.visibility = View.VISIBLE
            ViewAnimationUtils.createCircularReveal(view, x, y, 0f, finalRadius)
        } else {
            ViewAnimationUtils.createCircularReveal(view, x, y, finalRadius, 0f)
                .apply {
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animator: Animator) {
                            view.visibility = View.INVISIBLE
                            doOnHidden?.invoke()
                        }
                    })
                }
        }
    circularReveal.duration = 800
    circularReveal.start()
}