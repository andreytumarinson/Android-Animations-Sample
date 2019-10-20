package com.example.androidanimations.fragmenttransitition.grid.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.example.androidanimations.R
import com.example.androidanimations.utils.sampleGridData
import kotlinx.android.synthetic.main.fragment_frag_tr_grid.*
import kotlinx.android.synthetic.main.grid_item.view.*


class GridFragment : Fragment(),
    ViewHolderListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_frag_tr_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GridAdapter(sampleGridData, this)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        exitTransition = TransitionInflater.from(context).inflateTransition(R.transition.grid_exit_transition)

        //because RecyclerView items are bent asynchronously
        postponeEnterTransition()
    }

    override fun onLoadCompleted(position: Int) {
        startPostponedEnterTransition()
    }

    override fun onItemClicked(view: View, position: Int) {

        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        (exitTransition as? Transition)?.excludeTarget(view, true)

        fragmentManager
            ?.beginTransaction()
            ?.setReorderingAllowed(true) // Optimize for shared element transition
            ?.addSharedElement(view.image, view.image.transitionName)
            ?.replace(R.id.container, DetailsFragment.newInstance(sampleGridData[position], position))
            ?.addToBackStack(null)
            ?.commit()
    }
}
