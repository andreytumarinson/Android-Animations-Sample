package com.example.androidanimations.circle


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_reveal_sample.*
import kotlin.math.hypot

class RevealSamplesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reveal_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBtn.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.add(R.id.frag, RevealFragment())
                ?.commit()
        }

        activityBtn.setOnClickListener { startActivity(Intent(context, RevealActivity::class.java)) }

        info.setOnClickListener {
            if(card.visibility != View.VISIBLE) circularRevealView(card, 0, 0, true)
        }
        background.setOnClickListener {
            if(card.visibility == View.VISIBLE) circularRevealView(card, 0, 0, false)
        }
    }

    private fun circularRevealView(view: View, x: Int, y: Int, show: Boolean, doOnHidden: (()-> Unit)? = null) {
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
        circularReveal.duration = 600
        circularReveal.start()
    }
}
