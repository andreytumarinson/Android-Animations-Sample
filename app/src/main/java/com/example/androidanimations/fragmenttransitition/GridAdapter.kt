package com.example.androidanimations.fragmenttransitition

import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.grid_item.view.*


interface OnItemClickListener {
    fun onItemClicked(position: Int, item: Item, imageView: ImageView)
}

class GridAdapter(private val items: List<Item>, private val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<GridAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_item, parent, false), itemClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        /*Picasso.with(holder.itemView.context)
            .load(animalItem.imageUrl)
            .into(holder.animalImageView)*/

        holder.bind(items[position])
    }

    inner class ImageViewHolder(itemView: View, itemClickListener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {

        private var item: Item? = null

        init {
            itemView.setOnClickListener {
                item?.let {
                    itemClickListener?.onItemClicked(adapterPosition, it, itemView.image)
                }
            }
        }

        fun bind(item: Item) {
            this.item = item
            itemView.image.transitionName = "f_${adapterPosition}"
            itemView.image.setImageResource(item.image)
            itemView.text.text = item.name
        }
    }
}