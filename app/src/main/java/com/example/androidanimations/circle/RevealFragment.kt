package com.example.androidanimations.circle


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_reveal.*
import kotlin.math.hypot
import kotlin.math.max


interface OnBackPressedListener {
    fun onBackPressed()
}

class RevealFragment : Fragment(), OnBackPressedListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reveal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        root.visibility = View.INVISIBLE
        root.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(rootLayout: View?, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int, p7: Int, p8: Int) {
                rootLayout?.removeOnLayoutChangeListener(this)
                rootLayout?.let { circularRevealFragment(true) }
            }
        })
    }


    override fun onBackPressed() {
        circularRevealFragment(false)
    }

    private fun circularRevealFragment(show: Boolean) {
        val cx = root.left
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
                                fragmentManager?.popBackStack()
                            }
                        })
                    }
            }
        circularReveal.duration = 800
        circularReveal.start()
    }



    private fun startCircularRevealAnimation(v: View) {

        val centerX = (v.right - v.left) / 2
        val centerY = (v.bottom - v.top) / 2
        /* val centerX = (v.left + v.right) / 2
         val centerY = v.top*/

        val radius = max(v.width, v.height) * 2.0f
        //val radius = hypot(v.measuredWidth.toDouble(), v.measuredHeight.toDouble()).toFloat()

        // If the view is invisible state
        if (v.visibility != View.VISIBLE) {
            v.visibility = View.VISIBLE
            ViewAnimationUtils.createCircularReveal(v, centerX, centerY, 0f, radius)
                    .apply { duration = 1000 }.start()
        } else { // If the view is visible state
            ViewAnimationUtils.createCircularReveal(v, centerX, centerY, radius, 0f).apply {
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        v.visibility = View.INVISIBLE
                    }
                })
                duration = 1000
            }.start()
        }
    }

}
