package com.example.androidanimations.fragmenttransitition.grid.pager


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_frag_tr_details.*


class VPDetailsFragment : Fragment() {

    companion object {
        private const val EXTRA_ITEM = "tutorial_item"
        private const val EXTRA_POSITION = "position"
        private const val IS_LOCAL = "is_local"

        fun newInstance(item: Item, position: Int, isLocal: Boolean): VPDetailsFragment {
            return VPDetailsFragment()
                .apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_ITEM, item)
                    putInt(EXTRA_POSITION, position)
                    putBoolean(IS_LOCAL, isLocal)
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_frag_tr_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable(EXTRA_ITEM) as Item
        val position = arguments?.getInt(EXTRA_POSITION) ?: 0
        val isLocal = arguments?.getBoolean(IS_LOCAL) ?: true

        titleTV.text = item.name
        imageView.transitionName = "image_$position"

        if(isLocal) {
            imageView.setImageResource(item.image as Int)
            parentFragment?.startPostponedEnterTransition()
        } else {
            Glide.with(this)
                .load(item.image)
                .dontTransform()
                .transition(withCrossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()))
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        parentFragment?.startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        parentFragment?.startPostponedEnterTransition()
                        return false
                    }
                })
                .into(imageView)
        }
    }
}
