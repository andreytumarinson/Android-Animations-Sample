package com.example.androidanimations.motion.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.activity_motion_fragment.*
import kotlin.math.abs

class MotionFragmentsActivity : AppCompatActivity(), MotionLayout.TransitionListener {

    private var lastProgress = 0f
    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_fragment)
        if (savedInstanceState == null) {
            fragment = FirstFragment().also {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it)
                    .commitNow()
            }
        }
        motionLayout.setTransitionListener(this)
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

        if (p3 == 1f || p3 == 0f) return

        if (p3 - lastProgress > 0) {
            // from start to end
            val atEnd = abs(p3 - 1f) < 0.5f
            if (atEnd && fragment is FirstFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                fragment = SecondFragment().also {
                    transaction
                        .replace(R.id.container, it)
                        .commitNow()
                }
            }
        } else {
            // from end to start
            val atStart = p3 < 0.5f
            if (atStart && fragment is SecondFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out
                )
                fragment = FirstFragment().also {
                    transaction
                        .replace(R.id.container, it)
                        .commitNow()
                }
            }
        }

        fragment?.let {
            if (it is SecondFragment) it.motionLayout?.progress = p3
        }

        lastProgress = p3
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }


}
