package com.example.androidanimations


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.fragment_animations.button2
import kotlinx.android.synthetic.main.fragment_animations.button3
import kotlinx.android.synthetic.main.fragment_object_animator.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ObjectAnimatorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_object_animator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationX = ObjectAnimator.ofFloat(button2, "scaleX", 2F)
        val animationY = ObjectAnimator.ofFloat(button2, "scaleY", 2F)
        val set = AnimatorSet()
        set.play(animationX)
            .with(animationY)
        set.duration = 2000
        set.interpolator = DecelerateInterpolator()

        val translateAnimator = (AnimatorInflater.loadAnimator(context, R.animator.translate_animator) as ObjectAnimator).apply { target = button3 }

        val rotationAnimator = AnimatorInflater.loadAnimator(context, R.animator.rotate_animator) as ObjectAnimator
        rotationAnimator.target = button5

        val alphaAnimator = (AnimatorInflater.loadAnimator(context, R.animator.animator_alpha) as ObjectAnimator).apply { target = button4 }

        button.setOnClickListener {
            translateAnimator.start()
            rotationAnimator.start()
            alphaAnimator.start()
            set.start()
        }

        button3.setOnClickListener {
            translateAnimator.end()
        }

        button5.setOnClickListener {
            button5.animate().rotationYBy(-30f).setDuration(2000)
                .withEndAction {
                    button5.animate().rotationYBy(30f).setDuration(2000).start()
                }.start()
        }
    }
}
