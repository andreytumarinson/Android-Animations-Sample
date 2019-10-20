package com.example.androidanimations.fragmenttransitition


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.transition.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import kotlinx.android.synthetic.main.fragment_fr2.*
import com.example.androidanimations.R


class FragmentTrans2 : Fragment() {

    companion object {
        private const val TYPE = "fff"

        fun getInstance(type: Type): FragmentTrans2 {
            return FragmentTrans2().apply {
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
            Type.TRAN_SHARE_CUSTOM,
            Type.TRAN_SHARE_REMOTE-> {
                val tr = TransitionInflater.from(context).inflateTransition(R.transition.transition_2)
                    .apply { duration = 300 }
                    .addListener(object : Transition.TransitionListener {
                        override fun onTransitionEnd(transition: Transition) {
                            floatingActionButton?.show()
                            floatingActionButton?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up))
                        }

                        override fun onTransitionResume(transition: Transition) { }

                        override fun onTransitionPause(transition: Transition) {}

                        override fun onTransitionCancel(transition: Transition) {}

                        override fun onTransitionStart(transition: Transition) {}
                    })

                enterTransition = tr.clone().apply { startDelay = 400 }
                returnTransition = tr
            }
        }

        when (transitionType) {
            Type.TRAN_SHARE,
            Type.TRAN_SHARE_ENT_EX,
            Type.TRAN_SHARE_2,
            Type.TRAN_SHARE_2_ENT_EX -> {
                sharedElementEnterTransition = TransitionSet()
                    .addTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.move))
            }
            Type.TRAN_SHARE_CUSTOM -> {
                val shTr = TransitionSet()
                    .addTransition(TransitionInflater.from(activity).inflateTransition(android.R.transition.move))
                    .addTransition(TextSizeTransition())
                sharedElementEnterTransition = shTr.clone().apply { startDelay = 100 }
                sharedElementReturnTransition = shTr.clone().apply { startDelay = 100 }
            }
            Type.TRAN_SHARE_REMOTE -> {
                val shTr = TransitionSet()
                    .addTransition(TransitionInflater.from(activity).inflateTransition(R.transition.image_shared_element_transition))
                    .addTransition(TextSizeTransition())
                sharedElementEnterTransition = shTr.clone().apply { startDelay = 100 }
                sharedElementReturnTransition = shTr.clone().apply { startDelay = 100 }
            }
            else -> {
                sharedElementEnterTransition = null
                sharedElementReturnTransition = null
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fr2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       if(transitionType in listOf(Type.TRAN_SHARE_CUSTOM, Type.TRAN_SHARE_REMOTE)) floatingActionButton.hide()

        if(transitionType == Type.TRAN_SHARE_REMOTE) {
            postponeEnterTransition()

            Glide.with(this)
                .load("https://www.jetsetter.com/uploads/sites/7/2018/04/rp47F1oo-1380x690.jpeg")
                .dontTransform()
                .transition(
                    DrawableTransitionOptions.withCrossFade(
                        DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                    )
                )
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }
                })
                .into(image)
        }
    }
}
