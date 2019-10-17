package com.example.androidanimations.fragmenttransitition


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.androidanimations.R
import androidx.transition.TransitionInflater
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_view_pager.*
import com.example.androidanimations.MainActivity
import androidx.core.app.SharedElementCallback
import kotlinx.android.synthetic.main.grid_item.view.*
import androidx.viewpager.widget.ViewPager




/**
 * A simple [Fragment] subclass.
 */
class ViewPagerFragment : Fragment() {

    companion object {
        private const val EXTRA_INITIAL_ITEM_POS = "initial_item_pos"
        private const val EXTRA_ANIMAL_ITEMS = "animal_items"

        fun newInstance(currentItem: Int, items: List<Item>): ViewPagerFragment {
            return ViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_INITIAL_ITEM_POS, currentItem)
                    putSerializable(EXTRA_ANIMAL_ITEMS, ArrayList(items))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //postponeEnterTransition()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentItem = arguments!!.getInt(EXTRA_INITIAL_ITEM_POS)
        val animalItems = arguments!!.getSerializable(EXTRA_ANIMAL_ITEMS) as List<Item>

        val animalPagerAdapter = ViewPagerAdapter(childFragmentManager, animalItems)

        viewPager.adapter = animalPagerAdapter
        viewPager.currentItem = currentItem

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                MainActivity.currentPosition = position
            }
        })

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        //sharedElementReturnTransition = null

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
                        ?.instantiateItem(viewPager, MainActivity.currentPosition) as Fragment
                    val view1 = currentFragment.view ?: return

                    Log.e("dd", "setEnterSharedElementCallback ${MainActivity.currentPosition} ${view1.findViewById<View>(R.id.imageView)}")
                    // Map the first shared element name to the child ImageView.
                    sharedElements[/*"f_"+MainActivity.currentPosition*/names[0]] = view1.findViewById<View>(R.id.imageView)
                }
            })

    }


}
