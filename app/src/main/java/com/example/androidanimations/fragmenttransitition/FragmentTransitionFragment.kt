package com.example.androidanimations.fragmenttransitition

import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_fragment_transition.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentTransitionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_transition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.NO)) }
        button1.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.ENT_EX_DEF)) }
        button2.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.CUST_ANIM)) }
        button3.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.TRAN_ENT_EX)) }
        button4.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.TRAN_SHARE)) }
        button5.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.TRAN_SHARE_ENT_EX)) }
        button6.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.TRAN_SHARE_TOP)) }



        //openScreen(FragTrFragment1(), false)
    }

    fun openScreen1() {
        //openScreen(FragTrFragment2()/*.apply { enterTransition = Slide(Gravity.BOTTOM) }*/, true)
        //openScreen(FragTrGridFragment(), true)
    }

    fun openScreen2() {
        val previousFragment = childFragmentManager.findFragmentById(R.id.frame)
        val exitFade = Explode()
        exitFade.duration = 1000
        previousFragment?.exitTransition = exitFade


        val nextFragment = FragTrFragment2()

        val enterTransitionSet = TransitionSet()
        enterTransitionSet.addTransition(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
        enterTransitionSet.duration = 1000
        enterTransitionSet.startDelay = 0
        //nextFragment.sharedElementEnterTransition = enterTransitionSet

        val enterFade = Fade()
        //enterFade.startDelay = 300
        enterFade.duration = 300
        nextFragment.enterTransition = enterFade


        childFragmentManager?.beginTransaction()
            //.setReorderingAllowed(true)
         //   ?.addSharedElement(imageView, imageView.transitionName)
            ?.replace(R.id.frame, nextFragment)
            ?.addToBackStack(null)
            ?.commit()
            //.commitAllowingStateLoss()

       // openScreen(FragTrFragment2(), true)
    }

    fun openScreen3(item: Item) {
     //   val fragment = FragTrDetailsFragment.newInstance(item, imageView.transitionName)
       // fragment.enterTransition //= (Slide(Gravity.BOTTOM))

         TransitionSet()
        .addTransition(Fade()
            .excludeTarget(R.id.title, true)
            .excludeTarget(R.id.details, true)
            .apply { duration = 500 })
         .addTransition(Slide(Gravity.BOTTOM)
             .addTarget(R.id.title)
             .addTarget(R.id.details))


        fragmentManager
            ?.beginTransaction()
            //?.setReorderingAllowed(true)
          //  ?.addSharedElement(imageView, imageView.transitionName)
            ?.addToBackStack("dd")
          //  ?.replace(R.id.frame, fragment)
            ?.commit()
    }

    private fun openScreen(frag: Fragment) {
        fragmentManager?.beginTransaction()
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_right_in, R.animator.slide_left_out,
            //    R.animator.slide_left_in, R.animator.slide_right_out)
            ?.replace(R.id.container, frag, "openFrag")
            ?.addToBackStack(null)
            ?.commit()
    }
}
