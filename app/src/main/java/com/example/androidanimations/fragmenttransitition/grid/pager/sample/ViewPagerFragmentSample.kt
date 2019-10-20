package com.example.androidanimations.fragmenttransitition.grid.pager.sample


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
import androidx.transition.Fade
import androidx.viewpager.widget.ViewPager
import com.example.androidanimations.fragmenttransitition.grid.pager.PositionHolder


/**
 * A simple [Fragment] subclass.
 */
class ViewPagerFragmentSample : Fragment() {

    companion object {
        private const val EXTRA_INITIAL_ITEM_POS = "initial_item_pos"
        private const val EXTRA_ITEMS = "items"

        fun newInstance(currentItem: Int, items: List<Item>): ViewPagerFragmentSample {
            return ViewPagerFragmentSample().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_INITIAL_ITEM_POS, currentItem)
                    putSerializable(EXTRA_ITEMS, ArrayList(items))
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

        val pagerAdapter = ViewPagerAdapterSample(childFragmentManager, items)

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
        enterTransition = Fade()

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(names: List<String>,
                                                 sharedElements: MutableMap<String, View>) {
                    // Locate the image view at the primary fragment (the ImageFragment
                    // that is currently visible). To locate the fragment, call
                    // instantiateItem with the selection position.
                    // At this stage, the method will simply return the fragment at the
                    // position and will not create a new one.
                    val currentFragment = viewPager.adapter
                        ?.instantiateItem(viewPager, PositionHolder.currentItemPosition) as Fragment
                    val view = currentFragment.view ?: return

                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] = view.findViewById(R.id.imageView)
                }
            })
    }


}
