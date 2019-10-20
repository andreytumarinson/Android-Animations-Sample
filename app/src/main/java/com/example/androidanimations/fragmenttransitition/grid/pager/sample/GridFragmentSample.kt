package com.example.androidanimations.fragmenttransitition.grid.pager.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.TransitionSet
import com.example.androidanimations.R
import com.example.androidanimations.fragmenttransitition.grid.pager.PositionHolder
import com.example.androidanimations.utils.sampleGridData
import kotlinx.android.synthetic.main.fragment_frag_tr_grid.*
import kotlinx.android.synthetic.main.grid_item.view.*
import java.util.concurrent.atomic.AtomicBoolean


class GridFragmentSample : Fragment(), ViewHolderListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_frag_tr_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GridAdapterSample(sampleGridData, this)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        prepareSharedTransition()

        postponeEnterTransition()

        scrollToPosition()
    }

    private fun prepareSharedTransition() {
        exitTransition = TransitionSet()
            .addTransition(Explode())
            .addTransition(Fade())

        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                    // Locate the ViewHolder for the clicked position.
                    val selectedViewHolder = recyclerView.findViewHolderForAdapterPosition(
                        PositionHolder.currentItemPosition)
                    selectedViewHolder?.itemView?: return

                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] = selectedViewHolder.itemView.findViewById(R.id.image)
                }
            })
    }

    private fun scrollToPosition() {
        recyclerView.addOnLayoutChangeListener(object : OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View, left: Int, top: Int, right: Int, bottom: Int,
                oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
            ) {
                recyclerView.removeOnLayoutChangeListener(this)
                val layoutManager = recyclerView.layoutManager
                val viewAtPosition = layoutManager?.findViewByPosition(PositionHolder.currentItemPosition)
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
                    recyclerView.post { layoutManager?.scrollToPosition(PositionHolder.currentItemPosition) }
                }
            }
        })
    }

    //todo necessary only with Picasso and Glide
    private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()

    override fun onLoadCompleted(view: ImageView, position: Int) {
        // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
        if (PositionHolder.currentItemPosition != position) {
            return
        }
        /*if (enterTransitionStarted.getAndSet(true)) {
            return
        }*/
        startPostponedEnterTransition()
    }

    /**
     * Handles a view click by setting the current position to the given `position` and
     * starting a [ImagePagerFragment] which displays the image at the position.
     *
     * @param view the clicked [ImageView] (the shared element view will be re-mapped at the
     * GridFragment's SharedElementCallback)
     * @param position the selected view position
     */
    override fun onItemClicked(view: View, position: Int) {
        PositionHolder.currentItemPosition = position

        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        (exitTransition as? TransitionSet)?.excludeTarget(view, true)

        val transitioningView = view.image
        fragmentManager
            ?.beginTransaction()
            ?.setReorderingAllowed(true) // Optimize for shared element transition
            ?.addSharedElement(transitioningView, transitioningView.transitionName)
            ?.replace(R.id.container, ViewPagerFragmentSample.newInstance(position, sampleGridData))
            ?.addToBackStack(null)
            ?.commit()
    }
}
