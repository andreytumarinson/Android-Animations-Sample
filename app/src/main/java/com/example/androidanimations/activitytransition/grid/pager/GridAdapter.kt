package com.example.androidanimations.activitytransition.grid.pager

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.grid_item.view.*

interface ViewHolderListener {
    fun onLoadCompleted(position: Int)

    fun onItemClicked(view: View, position: Int)
}

class GridAdapter(private val items: List<Item>, private val isLocal: Boolean,
                  private val viewHolderListener: ViewHolderListener)
    : RecyclerView.Adapter<GridAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_item, parent, false), isLocal, viewHolderListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ImageViewHolder(itemView: View, private val isLocal: Boolean,
                                private val viewHolderListener: ViewHolderListener) : RecyclerView.ViewHolder(itemView) {

        private var item: Item? = null

        init {
            itemView.setOnClickListener {
                viewHolderListener.onItemClicked(itemView, adapterPosition)
            }
        }

        fun bind(item: Item) {
            this.item = item

            itemView.text.text = item.name
            itemView.image.transitionName = "image_$adapterPosition"

            if(isLocal) {
                itemView.image.setImageResource(item.image as Int)
                viewHolderListener.onLoadCompleted(adapterPosition)
            } else {
                Glide.with(itemView)
                    .load(item.image)
                    .dontTransform()
                    .transition(
                        DrawableTransitionOptions.withCrossFade(
                            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                        )
                    )
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            viewHolderListener.onLoadCompleted(adapterPosition)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            viewHolderListener.onLoadCompleted(adapterPosition)
                            return false
                        }
                    })
                    .into(itemView.image)
            }
        }
    }
}