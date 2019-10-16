package com.example.androidanimations.fragmenttransitition


import android.os.Bundle
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Slide

import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_frag_tr_fragment1.*

/**
 * A simple [Fragment] subclass.
 */
class FragTrFragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = Fade().excludeTarget(R.id.imageView, true)//.apply { startDelay = 400 }
      //  reenterTransition = Fade().apply { startDelay = 400 }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_tr_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener { /*(parentFragment as FragmentTransitionFragment).openScreen1()*/
            openScreen(FragTrGridFragment(), true)

        }

        button2.setOnClickListener {
            fragmentManager?.beginTransaction()
                //.setReorderingAllowed(true)
                ?.addSharedElement(imageView, imageView.transitionName)
                ?.replace(R.id.container, FragTrFragment2())
                ?.addToBackStack(null)
                ?.commit()


        }
    }



    private fun openScreen(frag: Fragment, backStack: Boolean) {
        fragmentManager?.beginTransaction()
            //.setCustomAnimations(R.animator.slide_right_in, R.animator.slide_left_out,
            //    R.animator.slide_left_in, R.animator.slide_right_out)
            ?.replace(R.id.container, frag, "openFrag")
            ?.apply { if(backStack) addToBackStack(null)}
            ?.commit()
    }
}
