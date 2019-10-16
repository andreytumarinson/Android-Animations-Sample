package com.example.androidanimations.lottie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_lottie.*


/**
 * A simple [Fragment] subclass.
 *
 */
class LottieFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?  ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lottie, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottie.addLottieOnCompositionLoadedListener {
            progressBar?.visibility = View.GONE
        }


        val pagerAdapter = fragmentManager?.let {
            PagerAdapter(
                it
            )
        }
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (pagerAdapter != null) {
                    lottie.progress = (position + positionOffset) / (pagerAdapter.numPages - 1)
                }
            }

            override fun onPageSelected(position: Int) {}

        })

        tabs.setupWithViewPager(viewPager)
    }
}
