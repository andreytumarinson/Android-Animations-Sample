package com.example.androidanimations.activitytransition.grid.single

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidanimations.R
import com.example.androidanimations.utils.sampleRemoteGridData
import kotlinx.android.synthetic.main.fragment_frag_tr_grid.*
import kotlinx.android.synthetic.main.grid_item.view.*

class GridActivity : AppCompatActivity(),
    ViewHolderListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_frag_tr_grid)

        val adapter = GridAdapter(sampleRemoteGridData/*sampleGridData*/, this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        window.sharedElementsUseOverlay = false
        window.exitTransition = TransitionInflater.from(this).inflateTransition(R.transition.grid_exit_transition)

        //because RecyclerView items are bent asynchronously
        postponeEnterTransition()
    }

    override fun onLoadCompleted(position: Int) {
        startPostponedEnterTransition()
    }

    override fun onItemClicked(view: View, position: Int) {

        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        window.exitTransition?.excludeTarget(view, true)

        startActivity(Intent(this, DetailActivity::class.java)
            .apply {
                putExtra(DetailActivity.EXTRA_ITEM, /*sampleGridData*/sampleRemoteGridData[position])
                putExtra(DetailActivity.EXTRA_POSITION, position)
            }, ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair<View, String>(view.image, view.image.transitionName)).toBundle()
            )
    }
}
