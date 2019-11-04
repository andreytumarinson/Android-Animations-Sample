package com.example.androidanimations.fragmenttransitition.grid.pager

import android.os.Bundle
import android.util.Log
import androidx.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Transition
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import com.example.androidanimations.utils.sampleGridData
import com.example.androidanimations.utils.sampleRemoteGridData
import kotlinx.android.synthetic.main.fragment_frag_tr_grid.*
import kotlinx.android.synthetic.main.grid_item.view.*
import java.util.concurrent.atomic.AtomicBoolean

class VPGridFragment : Fragment(), ViewHolderListener {

    companion object {
        private const val IS_LOCAL = "is_local"

        fun newInstance(isLocal: Boolean): VPGridFragment {
            return VPGridFragment()
                .apply {
                    arguments = Bundle().apply {
                        putBoolean(IS_LOCAL, isLocal)
                    }
                }
        }
    }

    private var isLocal = true
    private lateinit var dataList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PositionHolder.currentItemPosition = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_frag_tr_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //enterTransitionStarted.getAndSet(false)

        isLocal = arguments?.getBoolean(IS_LOCAL) ?: true
        dataList = if(isLocal) sampleGridData else sampleRemoteGridData

        val adapter = GridAdapter(dataList, isLocal, this)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        prepareSharedTransition()

        postponeEnterTransition()

        scrollToPosition()
    }

    private fun prepareSharedTransition() {
        exitTransition = TransitionInflater.from(context).inflateTransition(R.transition.grid_exit_transition)

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
                    recyclerView.post { layoutManager?.scrollToPosition(PositionHolder.currentItemPosition) }}
            }
        })
    }

    //private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()

    override fun onLoadCompleted(position: Int) {
        // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
        // Necessary to avoid incorrect transition after auto scroll to position
        if (PositionHolder.currentItemPosition != position) {
            return
        }
        /*if (enterTransitionStarted.getAndSet(true)) {
            return
        }*/
        startPostponedEnterTransition()
    }

    override fun onItemClicked(view: View, position: Int) {
        PositionHolder.currentItemPosition = position

        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        (exitTransition as? Transition)?.excludeTarget(view, true)

        fragmentManager
            ?.beginTransaction()
            ?.setReorderingAllowed(true) // Optimize for shared element transition
            ?.addSharedElement(view.image, view.image.transitionName)
            ?.replace(R.id.container, ViewPagerFragment.newInstance(position, dataList, isLocal))
            ?.addToBackStack(null)
            ?.commit()
    }
}
