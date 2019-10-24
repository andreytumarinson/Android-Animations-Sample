package com.example.androidanimations.activitytransition

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import androidx.core.util.Pair
import android.view.Gravity
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.activity_at1.*

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

class ATActivity1 : AppCompatActivity() {

    companion object {
        const val TYPE = "fff"
    }

    private var transitionType = Type.NO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_at1)

        transitionType = intent.extras?.getSerializable(TYPE) as? Type ?: Type.NO

        when (transitionType) {
            Type.TRAN_ENT_EX -> {
                window.exitTransition = Fade()
                window.reenterTransition = Slide(Gravity.START)
            }
            Type.TRAN_SHARE_CUSTOM,
            Type.TRAN_SHARE_REMOTE-> {
                val tr = TransitionSet()
                    .addTransition(Fade()
                        .addTarget(textView8)
                        .addTarget(imageView3)
                        .addTarget(imageView4)
                        .addTarget(textLong)
                    )
                    .addTransition(
                        Slide(Gravity.BOTTOM)
                            .addTarget(bottom)
                    )
                window.exitTransition = tr
                window.reenterTransition = tr.clone().apply { startDelay = 300 }
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

        card.setOnClickListener { openScreen() }
    }

    private fun openScreen() {

        val intent = Intent(this, ATActivity2::class.java)
            .apply { putExtra(ATActivity2.TYPE, transitionType) }

        when (transitionType) {
            Type.NO -> {
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            Type.ENT_EX_DEF -> startActivity(intent)
            Type.CUST_ANIM -> {
                startActivity(intent)
                overridePendingTransition(R.anim.scale_up, R.anim.scale_down)
            }
            Type.TRAN_ENT_EX -> {
                startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
                )
            }
            Type.TRAN_SHARE,
            Type.TRAN_SHARE_ENT_EX -> {
                startActivity(intent, ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, image, image.transitionName).toBundle()
                )
            }
            Type.TRAN_SHARE_2,
            Type.TRAN_SHARE_2_ENT_EX -> {
                startActivity(intent, ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this,
                            Pair<View, String>(image, image.transitionName),
                            Pair<View, String>(card, card.transitionName)).toBundle()
                )
            }
            Type.TRAN_SHARE_CUSTOM,
            Type.TRAN_SHARE_REMOTE -> {
                startActivity(intent, ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this,
                        Pair<View, String>(image, image.transitionName),
                        Pair<View, String>(card, card.transitionName),
                        Pair<View, String>(titleTV, titleTV.transitionName)).toBundle()
                )

            }
        }
    }
}
