package com.example.androidanimations.circle

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import kotlin.math.hypot
import android.view.*
import androidx.appcompat.widget.Toolbar
import com.example.androidanimations.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.circle_reveal_app_bar.view.*

class RevealColorAppBarLayout: FrameLayout {

    private var x: Int = 0
    private var y: Int = 0
    private var animator: Animator? = null

    var colorList: List<Int>? = null

    val appBar: AppBarLayout get() = rootView.appBarLayout
    val toolbar: Toolbar get() = rootView.toolbarView
    val tabs: TabLayout get() = rootView.tabLayout

    fun setBackground(@ColorInt color: Int) {
        rootView.backgroundView.setBackgroundColor(color)
    }

    private val tabSelectListener = object: TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {

        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            colorList?.let {
                val color = if (it.size > tab.position) it[tab.position] else it[0]
                if (animator?.isRunning == true) animator?.cancel()
                animator = getChangeColorCircleAnimator(rootView.backgroundView, rootView.revealView, x, y, color)
                animator?.start()
            }
        }
    }

    constructor(context: Context): super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context)
    }

    //constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    private fun init(context: Context) {
        val inflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.circle_reveal_app_bar, this, true)

        //tabLayout.requestDisallowInterceptTouchEvent(false)
        rootView.tabLayout.onInterceptTouchListener = object : OnInterceptTouchListener {
            override fun onInterceptTouchEvent(ev: MotionEvent) {
                x = ev.rawX.toInt()
                y = ev.rawY.toInt()
            }
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        rootView.tabLayout.addOnTabSelectedListener(tabSelectListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        rootView.tabLayout.removeOnTabSelectedListener(tabSelectListener)
    }

    private fun getChangeColorCircleAnimator(backgroundView: View, revealView: View, x: Int, y: Int, @ColorInt color: Int): Animator {
        revealView.visibility = View.VISIBLE

        val finalRadius = hypot(backgroundView.width.toDouble(), backgroundView.height.toDouble()).toFloat()
        val animation = ViewAnimationUtils
                .createCircularReveal(revealView, x, y, 0f, finalRadius)

        animation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
            }
            override fun onAnimationEnd(animator: Animator) {
                backgroundView.setBackgroundColor(color)
                revealView.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {
                backgroundView.setBackgroundColor(color)
                revealView.visibility = View.INVISIBLE
            }
        })
        revealView.setBackgroundColor(color)
        animation.duration = 500

        return animation
    }
}