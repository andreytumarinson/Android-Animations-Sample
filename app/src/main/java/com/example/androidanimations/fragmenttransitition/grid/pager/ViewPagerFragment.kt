package com.example.androidanimations.fragmenttransitition.grid.pager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.androidanimations.R
import androidx.transition.TransitionInflater
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_view_pager.*
import androidx.core.app.SharedElementCallback
import androidx.viewpager.widget.ViewPager


class ViewPagerFragment : Fragment() {

    companion object {
        private const val EXTRA_INITIAL_ITEM_POS = "initial_item_pos"
        private const val EXTRA_ITEMS = "items"
        private const val IS_LOCAL = "is_local"

        fun newInstance(currentItem: Int, items: List<Item>, isLocal: Boolean): ViewPagerFragment {
            return ViewPagerFragment()
                .apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_INITIAL_ITEM_POS, currentItem)
                    putSerializable(EXTRA_ITEMS, ArrayList(items))
                    putBoolean(IS_LOCAL, isLocal)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentItem = arguments!!.getInt(EXTRA_INITIAL_ITEM_POS)
        val items = arguments!!.getSerializable(EXTRA_ITEMS) as List<Item>
        val isLocal = arguments?.getBoolean(IS_LOCAL) ?: true

        val pagerAdapter = ViewPagerAdapter(childFragmentManager, items, isLocal)

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
        sharedElementEnterTransition = TransitionInflater.from(context)
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
