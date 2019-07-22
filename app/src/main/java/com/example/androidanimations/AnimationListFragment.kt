package com.example.androidanimations


import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_animation_list.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AnimationListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animation_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animation1 = (image1.drawable as? AnimationDrawable)
        animation1?.start()

        image1.setOnClickListener {
            if(animation1?.isRunning == true) animation1.stop()
            else animation1?.start()
        }

        val animation2 = (image2.drawable as? AnimationDrawable)
        animation2?.isOneShot = false
        animation2?.start()

        image2.setOnClickListener {
            if(animation2?.isRunning == true) animation2.stop()
            else animation2?.start()
        }
    }


}
