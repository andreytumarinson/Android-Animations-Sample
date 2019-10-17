package com.example.androidanimations.fragmenttransitition


import android.os.Bundle
import androidx.transition.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_fr1.*
import kotlinx.android.synthetic.main.fragment_fr1.card
import kotlinx.android.synthetic.main.fragment_fr1.image
import kotlinx.android.synthetic.main.fragment_fr1.title
import com.example.androidanimations.R


enum class Type {
    NO,
    ENT_EX_DEF,
    CUST_ANIM,
    TRAN_ENT_EX,
    TRAN_SHARE,
    TRAN_SHARE_ENT_EX,
    TRAN_SHARE_TOP
}


/**
 * A simple [Fragment] subclass.
 */
class Fr1Fragment : Fragment() {

    companion object {
        private const val TYPE = "fff"

        fun getInstance(type: Type): Fr1Fragment {
            return Fr1Fragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TYPE, type)
                }
            }
        }
    }

    private var transitionType = Type.NO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        transitionType = arguments?.getSerializable(TYPE) as Type

        when (transitionType) {
            Type.TRAN_ENT_EX -> {
                exitTransition = Fade()//Slide(Gravity.START)
                //reenterTransition = Slide(Gravity.BOTTOM)
            }
            Type.TRAN_SHARE_ENT_EX -> {
                exitTransition = Fade()
            }
            Type.TRAN_SHARE_TOP -> {
                val tr = TransitionInflater.from(context).inflateTransition(R.transition.transition_1)
                    .apply { duration = 300 }
                   /*
                    TransitionSet()
                    .addTransition(Fade()
                        .addTarget(R.id.textLong)
                        .addTarget(R.id.imageView3)
                        .addTarget(R.id.imageView4)
                        .addTarget(R.id.textView8)*/
                        /*.excludeTarget(R.id.card, true)
                        //.excludeTarget(R.id.card2, true)
                        .excludeTarget(R.id.title, true)
                        .excludeTarget(R.id.textLong, true)
                        .excludeTarget(R.id.bottom, true)*/
                        // .excludeTarget(title, true)
                   /* )
                    .addTransition(
                        Slide(Gravity.BOTTOM)
                            .addTarget(R.id.bottom)
                    )
                    .apply { duration = 300 }*/


                exitTransition = tr
                reenterTransition = tr.clone().apply { startDelay = 400 }
            }
            else -> {
                exitTransition = null
                reenterTransition = null
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.apply {
                    if (transitionType == Type.ENT_EX_DEF) {
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    }
                }
                ?.apply {
                    if (transitionType == Type.CUST_ANIM) {
                        setCustomAnimations(R.animator.slide_right_in, R.animator.slide_left_out,
                                R.animator.slide_left_in, R.animator.slide_right_out)
                    }
                }
                ?.apply {
                    if (transitionType in listOf(Type.TRAN_SHARE, Type.TRAN_SHARE_ENT_EX, Type.TRAN_SHARE_TOP)) {
                        addSharedElement(image, image.transitionName)
                    }
                    if (transitionType in listOf(Type.TRAN_SHARE_ENT_EX, Type.TRAN_SHARE_TOP)) {
                        addSharedElement(card, card.transitionName)
                        addSharedElement(title, title.transitionName)
                    }
                }
                //?.setReorderingAllowed(false)
                //?.setReorderingAllowed(true)
                ?.addToBackStack("ddd")
                ?.replace(R.id.container, Fr2Fragment.getInstance(transitionType))
                ?.commit()
        }
    }




}
