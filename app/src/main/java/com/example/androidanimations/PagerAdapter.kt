package com.example.androidanimations

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val numPages = 4

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a LottieFragmentPage (defined as a static inner class below).
        return LottieFragmentPage.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "tab $position"
    }

    override fun getCount(): Int {
        return numPages
    }
}