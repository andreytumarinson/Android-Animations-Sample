package com.example.androidanimations.motion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.activity_collapsing_toolbar_start.*
import kotlinx.android.synthetic.main.dummy_list_item.view.*

class CollapseToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar_start)

        recyclerView.apply {
            //setHasFixedSize(true)
            adapter = DummyListAdapter()
            layoutManager = LinearLayoutManager(this@CollapseToolbarActivity)
        }

       /* Glide.with(this)
            .load(R.drawable.sponge)
            .apply(RequestOptions.circleCropTransform().fitCenter())
            .into(toolbar_image)*/


        // workaround for wrong state after view being clicked/touched on some points
        // https://issuetracker.google.com/issues/11280555
       /* motionLayout.transitionToEnd()
        Handler().postDelayed({motionLayout!!.apply{
            transitionToStart()
            //setTransitionListener(this@CollapseToolbarActivity)
        }}, 50)*/
    }
}


class DummyListAdapter : RecyclerView.Adapter<DummyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.dummy_list_item, parent, false) as ConstraintLayout)
    }

    override fun getItemCount() = 15

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.textView.text = "Dummy tutorial_item $position"
    }

    class ViewHolder(val layout: ConstraintLayout) : RecyclerView.ViewHolder(layout)

}