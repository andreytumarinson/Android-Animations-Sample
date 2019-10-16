package com.example.androidanimations.fragmenttransitition


import android.os.Bundle
import android.view.Gravity
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide

import com.example.androidanimations.R



/**
 * A simple [Fragment] subclass.
 */
class FragTrFragment2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
        enterTransitionSet.duration = 1000
        enterTransitionSet.startDelay = 300
        sharedElementEnterTransition = enterTransitionSet

        /*enterTransition = Fade()
            .excludeTarget(R.id.imageView, true)
            .excludeChildren(R.id.back, true)
            .apply {
            startDelay = 400
        }*/
       // returnTransition = tr

        val tr = TransitionSet()
            .addTransition(Fade()
                .excludeTarget(R.id.imageView, true)
                .excludeChildren(R.id.back, true)
                .apply {
                    startDelay = 400
                })
            /*.addTransition(
                Slide(Gravity.BOTTOM)
                .addTarget(R.id.textLong2)
            )
            .addTransition(Slide(Gravity.END)
                .addTarget(R.id.textView9)
                .addTarget(R.id.textView10)
            )*/
            .apply { duration = 300 }


        enterTransition = tr.clone().apply { startDelay = 400 }
       // returnTransition = tr
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_tr_fragment2, container, false)
    }


}
