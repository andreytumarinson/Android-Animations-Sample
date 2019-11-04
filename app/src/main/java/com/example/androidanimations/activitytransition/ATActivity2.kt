package com.example.androidanimations.activitytransition

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.activity_at2.*


class ATActivity2 : AppCompatActivity() {

    companion object {
        const val TYPE = "fff"
    }

    private var transitionType = Type.NO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_at2)

        transitionType = intent.extras?.getSerializable(TYPE) as? Type ?: Type.NO

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        when (transitionType) {
            Type.TRAN_ENT_EX -> {
                window.returnTransition = Slide(Gravity.END)
                window.enterTransition = Explode()
                window.allowEnterTransitionOverlap = false
            }
            Type.TRAN_SHARE_ENT_EX,
            Type.TRAN_SHARE_2_ENT_EX -> {
                window.enterTransition = Slide(Gravity.BOTTOM)
            }
            Type.TRAN_SHARE_CUSTOM,
            Type.TRAN_SHARE_REMOTE-> {
                window.sharedElementsUseOverlay = false

                val tr = TransitionSet()
                        .addTransition(Fade().addTarget(R.id.action_bar_container))
                        .addTransition(Slide(Gravity.BOTTOM)
                            .addTarget(textLong2)
                            //.apply { startDelay = 300 }
                        )
                        .addTransition(Slide(Gravity.END)
                            .addTarget(textView9)
                            .addTarget(textView10)
                           // .apply { startDelay = 300 }
                        )

                window.enterTransition = tr.clone().apply {
                        getTransitionAt(1).apply { startDelay = 300 }
                        getTransitionAt(2).apply { startDelay = 300 }
                    }
                    .addListener(object : Transition.TransitionListener {
                        override fun onTransitionEnd(transition: Transition) {
                            floatingActionButton?.show()
                            floatingActionButton?.startAnimation(AnimationUtils.loadAnimation(this@ATActivity2, R.anim.scale_up))
                        }

                        override fun onTransitionResume(transition: Transition) { }

                        override fun onTransitionPause(transition: Transition) {}

                        override fun onTransitionCancel(transition: Transition) {}

                        override fun onTransitionStart(transition: Transition) {}
                    })
                window.returnTransition = tr

                val shTr = TransitionSet()
                    .addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move))
                    //.addTransition(TextResize())
                    .addTransition(TextSizeTransition())
                window.sharedElementEnterTransition = shTr
                window.sharedElementReturnTransition = shTr.clone().apply { startDelay = 200 }
                setEnterSharedElementCallback(EnterSharedElementCallback(this,
                    resources.getDimension(R.dimen.text_size_start),
                    resources.getDimension(R.dimen.text_size_end)))

                floatingActionButton.hide()
            }
        }

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        floatingActionButton?.hide()
        super.onBackPressed()
        when (transitionType) {
            Type.NO -> overridePendingTransition(0, 0)
            Type.CUST_ANIM -> overridePendingTransition(R.anim.scale_up, R.anim.scale_down)
        }
    }
}
