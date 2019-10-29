package com.example.androidanimations.circle

import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reveal.*
import kotlin.math.max
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import com.example.androidanimations.R
import kotlin.math.hypot


class RevealActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reveal)

        if (savedInstanceState == null) {
            root.visibility = View.INVISIBLE

            val viewTreeObserver = root.viewTreeObserver

            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        circularRevealActivity(true)
                    }
                })
            }
        }
    }

    private fun circularRevealActivity(show: Boolean) {
        val cx = root.right
        val cy = root.bottom
        val finalRadius = hypot(root.width.toDouble(), root.height.toDouble()).toFloat()

        val circularReveal =
            if (show) {
                root.visibility = View.VISIBLE
                ViewAnimationUtils.createCircularReveal(root, cx, cy, 0f, finalRadius)
            } else {
                ViewAnimationUtils.createCircularReveal(root, cx, cy, finalRadius, 0f)
                    .apply {
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animator: Animator) {
                                root.visibility = View.INVISIBLE
                                finish()
                            }
                        })
                    }
            }
        circularReveal.duration = 800
        circularReveal.start()
    }

    override fun onBackPressed() {
        circularRevealActivity(false)
    }
}
