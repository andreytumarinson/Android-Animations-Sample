package com.example.androidanimations


import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import kotlinx.android.synthetic.main.fragment_animations.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AnimationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
        val alphaAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_animation)
        val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation)
        val scaleAnimationPers = AnimationUtils.loadAnimation(context, R.anim.scale_animation_pers)
        val scaleAnimationParent = AnimationUtils.loadAnimation(context, R.anim.scale_animation_parent)

        val translateAnimation = TranslateAnimation(0F, 150F, 0F, 0F)
        translateAnimation.duration = 2000
        translateAnimation.interpolator = AccelerateDecelerateInterpolator()
        translateAnimation.fillAfter = true
        translateAnimation.repeatCount = 3
        translateAnimation.repeatMode = Animation.RESTART

        button3.setOnClickListener { translateAnimation.cancel() }

        button.setOnClickListener {
            button2.startAnimation(scaleAnimation)
            button3.startAnimation(translateAnimation)
            button4.startAnimation(alphaAnimation)
            button5.startAnimation(rotateAnimation)
            button6.startAnimation(scaleAnimationPers)
            button7.startAnimation(scaleAnimationParent)
        }
    }
}
