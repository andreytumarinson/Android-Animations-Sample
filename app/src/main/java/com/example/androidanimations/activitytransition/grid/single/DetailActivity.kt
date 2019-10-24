package com.example.androidanimations.activitytransition.grid.single

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_frag_tr_details.*
import com.bumptech.glide.request.target.Target

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ITEM = "item"
        const val EXTRA_POSITION = "position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_frag_tr_details)
        prepareSharedTransition()

        val item = intent.extras?.getSerializable(EXTRA_ITEM) as Item
        val position = intent.extras?.getInt(EXTRA_POSITION)

        titleTV.text = item.name
        imageView.transitionName = "image_$position"

        //todo Glide sample
        postponeEnterTransition()

        Glide.with(this)
            .load(item.image)
            .dontTransform()
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
            .into(imageView)

        //todo Local load sample
        //imageView.setImageResource(item.image as Int)
    }

    private fun prepareSharedTransition() {
        window.sharedElementsUseOverlay = false
        window.enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade_without_system)
             .excludeTarget(imageView, true)

        window.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(R.transition.image_shared_element_transition)
    }
}
