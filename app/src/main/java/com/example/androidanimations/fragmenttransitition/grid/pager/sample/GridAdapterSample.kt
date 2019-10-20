package com.example.androidanimations.fragmenttransitition.grid.pager.sample

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.grid_item_sample.view.*

interface ViewHolderListener {
    fun onLoadCompleted(view: ImageView, position: Int)

    fun onItemClicked(view: View, position: Int)
}

class GridAdapterSample(private val items: List<Item>, private val viewHolderListener: ViewHolderListener)
    : RecyclerView.Adapter<GridAdapterSample.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_item_sample, parent, false), viewHolderListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ImageViewHolder(itemView: View, viewHolderListener: ViewHolderListener) : RecyclerView.ViewHolder(itemView) {

        private var item: Item? = null

        init {
            itemView.setOnClickListener {
                item?.let { viewHolderListener.onItemClicked(itemView, adapterPosition) }
            }
        }

        fun bind(item: Item) {
            this.item = item
            itemView.image.transitionName = "image_$adapterPosition"
            itemView.image.setImageResource(item.image as Int)

            viewHolderListener.onLoadCompleted(itemView.image, adapterPosition)
        }
    }
}