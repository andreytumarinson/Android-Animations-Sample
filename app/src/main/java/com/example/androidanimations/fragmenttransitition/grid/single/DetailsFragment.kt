package com.example.androidanimations.fragmenttransitition.grid.single



import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.Target
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_frag_tr_details.*


class DetailsFragment : Fragment() {

    companion object {
        private const val EXTRA_ITEM = "tutorial_item"
        private const val EXTRA_POSITION = "position"

        fun newInstance(item: Item, position: Int): DetailsFragment {
            return DetailsFragment()
                .apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_ITEM, item)
                    putInt(EXTRA_POSITION, position)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_frag_tr_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareSharedTransition()

        val item = arguments?.getSerializable(EXTRA_ITEM) as Item
        val position = arguments?.getInt(EXTRA_POSITION)

        titleTV.text = item.name
        imageView.transitionName = "image_$position"

        //todo Glide sample
        /*postponeEnterTransition()

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
            .into(imageView)*/

        imageView.setImageResource(item.image as Int)
    }

    private fun prepareSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
}
