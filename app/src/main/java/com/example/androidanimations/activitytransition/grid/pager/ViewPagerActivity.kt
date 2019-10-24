package com.example.androidanimations.activitytransition.grid.pager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import android.transition.TransitionInflater
import androidx.viewpager.widget.ViewPager
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_frag_tr_details.*
import kotlinx.android.synthetic.main.fragment_view_pager.*

class ViewPagerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_INITIAL_ITEM_POS = "initial_item_pos"
        const val EXTRA_ITEMS = "items"
        const val IS_LOCAL = "is_local"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_view_pager)

        val currentItem = intent.extras?.getInt(EXTRA_INITIAL_ITEM_POS) ?: 0
        val items = intent.extras?.getSerializable(EXTRA_ITEMS) as List<Item>
        val isLocal = intent.extras?.getBoolean(IS_LOCAL) ?: true

        val pagerAdapter = ViewPagerAdapter(supportFragmentManager, items, isLocal)

        viewPager.adapter = pagerAdapter
        viewPager.currentItem = currentItem

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                PositionHolder.currentItemPosition = position
            }
        })

        prepareSharedTransition()

        // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }
    }

    private fun prepareSharedTransition() {
        window.sharedElementsUseOverlay = false
        window.enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade_without_system)
            .excludeTarget(R.id.imageView, true)
        window.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.image_shared_element_transition)

        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                    // Locate the image view at the primary fragment (the ImageFragment
                    // that is currently visible). To locate the fragment, call
                    // instantiateItem with the selection position.
                    // At this stage, the method will simply return the fragment at the
                    // position and will not create a new one.
                    val currentFragment = viewPager.adapter?.instantiateItem(viewPager, PositionHolder.currentItemPosition) as Fragment
                    val view = currentFragment.view ?: return

                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] = view.findViewById(R.id.imageView)
                }
            })
    }
}
