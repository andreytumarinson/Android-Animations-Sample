package com.example.androidanimations.fragmenttransitition


import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.transition.*
import kotlinx.android.synthetic.main.fragment_fr2.*
import com.example.androidanimations.R
import java.util.zip.GZIPOutputStream

/**
 * A simple [Fragment] subclass.
 */
class Fr2Fragment : Fragment() {

    companion object {
        private const val TYPE = "fff"

        fun getInstance(type: Type): Fr2Fragment {
            return Fr2Fragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TYPE, type)
                }
            }
        }
    }

    private var transitionType = Type.NO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        transitionType = arguments?.getSerializable(TYPE) as Type

        when (transitionType) {
            Type.TRAN_ENT_EX -> {
                enterTransition = Slide(Gravity.END)
                returnTransition = Slide(Gravity.TOP)
            }
            Type.TRAN_SHARE_ENT_EX -> {
                exitTransition = Fade()
            }
            Type.TRAN_SHARE_TOP -> {
                val tr = TransitionInflater.from(context).inflateTransition(R.transition.transition_2)
                    .apply { duration = 300 }
                /*TransitionSet()
                    *//*.addTransition(Fade()
                            .addTarget(R.id.floatingActionButton)
                    )*//*
                    .addTransition(Slide(Gravity.BOTTOM)
                        .addTarget(R.id.textLong2)
                    )
                    .addTransition(Slide(Gravity.END)
                        .addTarget(R.id.textView9)
                        .addTarget(R.id.textView10)
                    )
                    .apply { duration = 300 }*/
                    .addListener(object : Transition.TransitionListener {
                        override fun onTransitionEnd(transition: Transition) {
                            floatingActionButton?.show()
                            floatingActionButton?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up))
                        }

                        override fun onTransitionResume(transition: Transition) {

                        }

                        override fun onTransitionPause(transition: Transition) {

                        }

                        override fun onTransitionCancel(transition: Transition) {

                        }

                        override fun onTransitionStart(transition: Transition) {
                        }

                    })

                enterTransition = tr.clone().apply { startDelay = 400 }
                returnTransition = tr
            }
            else -> {
                enterTransition = null
                returnTransition = null
            }
        }





        when (transitionType) {
            Type.TRAN_SHARE,
            Type.TRAN_SHARE_ENT_EX -> {
                sharedElementEnterTransition = TransitionSet()
                    .addTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.move))
            }
            Type.TRAN_SHARE_TOP -> {
                val shTr = TransitionSet()
                    .addTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.move))
                    .addTransition(TextSizeTransition())
                sharedElementEnterTransition = shTr.clone().apply { startDelay = 150 }
                sharedElementReturnTransition = shTr.clone().apply { startDelay = 200 }
            }
            else -> {
                sharedElementEnterTransition = null
                sharedElementReturnTransition = null
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       if(transitionType == Type.TRAN_SHARE_TOP) floatingActionButton.hide()
    }
}
