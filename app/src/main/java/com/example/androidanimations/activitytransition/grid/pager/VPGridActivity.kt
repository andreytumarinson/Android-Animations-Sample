package com.example.androidanimations.activitytransition.grid.pager

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.SharedElementCallback
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import com.example.androidanimations.utils.sampleGridData
import com.example.androidanimations.utils.sampleRemoteGridData
import kotlinx.android.synthetic.main.fragment_frag_tr_grid.*
import kotlinx.android.synthetic.main.grid_item.view.*
import java.io.Serializable
import java.util.concurrent.atomic.AtomicBoolean


class VPGridActivity : AppCompatActivity(), ViewHolderListener {

    companion object {
        const val IS_LOCAL = "is_local"
    }

    private var isLocal = true
    private lateinit var dataList: List<Item>

    //private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_frag_tr_grid)

        PositionHolder.currentItemPosition = 0

        //enterTransitionStarted.getAndSet(false)

        isLocal = intent.extras?.getBoolean(IS_LOCAL) ?: true
        dataList = if(isLocal) sampleGridData else sampleRemoteGridData

        val adapter = GridAdapter(dataList, isLocal, this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        prepareSharedTransition()
    }

    override fun onRestart() {
        super.onRestart()
        postponeEnterTransition()
        scrollToPosition()
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        Log.e("e", "onActivityReenter")
    }

    private fun prepareSharedTransition() {
        window.sharedElementsUseOverlay = false
        window.exitTransition = TransitionInflater.from(this).inflateTransition(R.transition.grid_exit_transition)

        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                    // Locate the ViewHolder for the clicked position.
                    val selectedViewHolder = recyclerView.findViewHolderForAdapterPosition(PositionHolder.currentItemPosition)
                    selectedViewHolder?.itemView ?: return
                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] =
                        selectedViewHolder.itemView.findViewById(R.id.image)
                }
            })
    }

    private fun scrollToPosition() {
        val layoutManager = recyclerView.layoutManager
        val viewAtPosition = layoutManager?.findViewByPosition(PositionHolder.currentItemPosition)
        // Scroll to position if the view for the current position is null (not currently part of
        // layout manager children), or it's not completely visible.
        if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
            recyclerView.post { layoutManager?.scrollToPosition(PositionHolder.currentItemPosition) }

            container.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    Log.e("e", "viewTreeObserver2")
                    container.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    startPostponedEnterTransition()
                }
            })
        } else {
            recyclerView.post { startPostponedEnterTransition() }
        }
    }

    override fun onLoadCompleted(position: Int) {

    }

    override fun onItemClicked(view: View, position: Int) {
        PositionHolder.currentItemPosition = position

        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        window.exitTransition?.excludeTarget(view, true)

        startActivity(
            Intent(this, ViewPagerActivity::class.java)
                .apply {
                    putExtra(ViewPagerActivity.EXTRA_ITEMS,
                        (if(isLocal) sampleGridData else sampleRemoteGridData) as Serializable)
                    putExtra(ViewPagerActivity.EXTRA_INITIAL_ITEM_POS, position)
                    putExtra(ViewPagerActivity.IS_LOCAL, isLocal)
                }, ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair<View, String>(view.image, view.image.transitionName)
            ).toBundle()
        )
    }
}
