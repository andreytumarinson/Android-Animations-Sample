package com.example.androidanimations.fragmenttransitition

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.androidanimations.utils.Item


class ViewPagerAdapter internal constructor(fm: FragmentManager, private val items: List<Item>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val item = items[position]
        return FragTrDetailsFragment.newInstance(item, "f_${position}")
    }

    override fun getCount(): Int {
        return items.size
    }

}