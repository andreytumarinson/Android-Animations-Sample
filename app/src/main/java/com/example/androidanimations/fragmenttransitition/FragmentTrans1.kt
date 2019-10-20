package com.example.androidanimations.fragmenttransitition


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_fr1.*


enum class Type {
    NO,
    ENT_EX_DEF,
    CUST_ANIM,
    TRAN_ENT_EX,
    TRAN_SHARE,
    TRAN_SHARE_ENT_EX,
    TRAN_SHARE_2,
    TRAN_SHARE_2_ENT_EX,
    TRAN_SHARE_CUSTOM,
    TRAN_SHARE_REMOTE
}

class Fr1Fragment : Fragment() {

    companion object {
        private const val TYPE = "fff"

        fun getInstance(type: Type): Fr1Fragment {
            return Fr1Fragment().apply {
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
            Type.TRAN_ENT_EX,
            Type.TRAN_SHARE_ENT_EX,
            Type.TRAN_SHARE_2_ENT_EX -> exitTransition = Fade()

            Type.TRAN_SHARE_CUSTOM,
            Type.TRAN_SHARE_REMOTE-> {
                val tr = TransitionInflater.from(context).inflateTransition(R.transition.transition_1)
                    .apply { duration = 300 }
                exitTransition = tr
                reenterTransition = tr.clone().apply { startDelay = 400 }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fr1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card.setOnClickListener { openScreen() }

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

    private fun openScreen() {
        fragmentManager?.beginTransaction()
            ?.apply {
                if (transitionType == Type.ENT_EX_DEF) {
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                }
            }
            ?.apply {
                if (transitionType == Type.CUST_ANIM) {
                    setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out,
                        R.animator.flip_left_in, R.animator.flip_left_out)
                }
            }
            ?.apply {
                if (transitionType in listOf(Type.TRAN_SHARE, Type.TRAN_SHARE_ENT_EX, Type.TRAN_SHARE_2,
                        Type.TRAN_SHARE_2_ENT_EX, Type.TRAN_SHARE_CUSTOM, Type.TRAN_SHARE_REMOTE)) {
                    addSharedElement(image, image.transitionName)
                }
                if (transitionType in listOf(Type.TRAN_SHARE_2, Type.TRAN_SHARE_2_ENT_EX,
                         Type.TRAN_SHARE_CUSTOM, Type.TRAN_SHARE_REMOTE)) {
                    addSharedElement(card, card.transitionName)
                }
                if (transitionType in listOf(Type.TRAN_SHARE_CUSTOM, Type.TRAN_SHARE_REMOTE)) {
                    addSharedElement(title, title.transitionName)
                }
            }
            ?.addToBackStack(null)
            ?.replace(R.id.container, FragmentTrans2.getInstance(transitionType))
            ?.commit()
    }




}
