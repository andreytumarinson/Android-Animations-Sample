package com.example.androidanimations.circle

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.example.androidanimations.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_circle.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlin.math.hypot


class CircleActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle)

        appBarLayout.toolbar.title = "Circle Animations"
        appBarLayout.setBackground(resources.getColor(R.color.tabColor1))
        appBarLayout.colorList = listOf(
            resources.getColor(R.color.tabColor1),
            resources.getColor(R.color.tabColor2),
            resources.getColor(R.color.tabColor3))

        viewPager.adapter = SimpleFragmentPagerAdapter(this, supportFragmentManager)
        appBarLayout.tabs.setupWithViewPager(viewPager)
        appBarLayout.tabs.addOnTabSelectedListener(this)

        fab.setOnClickListener { startDialogShowing() }
    }

    override fun onDestroy() {
        super.onDestroy()
        appBarLayout.tabs.removeOnTabSelectedListener(this)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.fragments.last()
        if (frag != null && frag is OnBackPressedListener) {
            frag.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun onTabReselected(p0: TabLayout.Tab?) { }

    override fun onTabUnselected(p0: TabLayout.Tab?) { }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        if (p0?.position == 2) fab.show()
        else fab.hide()
    }

    private fun startDialogShowing() {
        moveButton(true) { showDialog() }
    }

    private fun startDialogHiding(dialogView: View, dialog: AlertDialog) {
        dialogCircleReveal(dialogView, false) {
            dialog.dismiss()
            moveButton(false)
        }
    }

    private fun moveButton(show: Boolean, doOnMoved: (() -> Unit)? = null) {
        val tr = TransitionSet()
           // .addTransition(AutoTransition())
            .addTransition(
                ChangeTransform().apply {
                    pathMotion = ArcMotion()
                    interpolator = LinearOutSlowInInterpolator()
                    duration = 300
                    addListener(object : Transition.TransitionListener {
                        override fun onTransitionResume(transition: Transition?) {}

                        override fun onTransitionPause(transition: Transition?) {}

                        override fun onTransitionCancel(transition: Transition?) {}

                        override fun onTransitionStart(transition: Transition?) {}

                        override fun onTransitionEnd(transition: Transition?) {
                            doOnMoved?.invoke()
                        }
                    })
                }
            )

        TransitionManager.beginDelayedTransition(container, tr)

        fab.translationY = if(show) -(fab.y / 2f - 12 * resources.displayMetrics.density) else 0f
        fab.translationX = if(show) -(fab.x / 2f - 12 * resources.displayMetrics.density) else 0f
        fab.imageAlpha = if(show) 0 else 255
    }

    private fun showDialog() {
        val dialogView = View.inflate(this, R.layout.dialog_view, null)

        AlertDialog.Builder(this)
            .create().apply {
                setView(dialogView)
                setCancelable(false)
                setOnShowListener { dialogCircleReveal(dialogView, true) }
                dialogView.btnSave.setOnClickListener { startDialogHiding(dialogView, this) }
                dialogView.btnCancel.setOnClickListener { startDialogHiding(dialogView, this) }
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }.show()
    }

    private fun dialogCircleReveal(rootView: View, show: Boolean, onDialogClosed: (() -> Unit)? = null) {
        val view = rootView.dialogView
        val x = view.width / 2
        val y = view.height / 2
        val maxRadius = hypot(view.width.toDouble(), view.height.toDouble()).toFloat()

        if (show) {
            view.visibility = View.VISIBLE
            ViewAnimationUtils.createCircularReveal(view, x, y, 0f, maxRadius)
                .apply { duration = 600 }.start()
        } else {
            ViewAnimationUtils.createCircularReveal(view, x, y, maxRadius, 0f)
                .apply {
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            view.visibility = View.INVISIBLE
                            onDialogClosed?.invoke()
                        }
                    })
                    duration = 400
                }.start()
        }
    }
}

class SimpleFragmentPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RippleDefaultFragment()
            1 -> RippleCustomFragment()
            else -> RevealSamplesFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Ripple 1"
            1 -> "Ripple 2"
            else -> "Reveal"
        }
    }
}
