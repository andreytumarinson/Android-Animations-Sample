package com.example.androidanimations


import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_vector_animation.*


/**
 * A simple [Fragment] subclass.
 *
 */
class VectorAnimationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vector_animation, container, false)
    }

    override fun onStart() {
        super.onStart()
        (image.drawable as? AnimatedVectorDrawable)?.start()
        (loader.drawable as? AnimatedVectorDrawable)?.start()
    }

}
