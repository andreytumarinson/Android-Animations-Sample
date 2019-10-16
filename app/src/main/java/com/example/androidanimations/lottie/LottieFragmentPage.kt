package com.example.androidanimations.lottie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_lottie_page.*


/**
 * A placeholder fragment containing a simple view.
 */
class LottieFragmentPage : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_lottie_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anim = when (arguments?.getInt(ARG_SECTION_NUMBER, 0)) {
            0 -> R.raw.lottie
            1 -> R.raw.finger
            2 -> R.raw.furniture
            else -> R.raw.checked_done
            /*0 -> "lottie.json"
            1 -> "finger.json"
            2 -> "furniture.json"
            else -> "checked_done.json"*/
        }

        animationView.setAnimation(anim)

        animationView.addLottieOnCompositionLoadedListener {
            progressBar?.visibility = View.GONE
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.e("jjj","onProgressChanged $progress")
                animationView.scale = progress.toFloat() / (seekBar.max /** 0.9F*/)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        animationView.playAnimation()
    }

    override fun onPause() {
        super.onPause()
        animationView.cancelAnimation()
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): LottieFragmentPage {
            return LottieFragmentPage().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}