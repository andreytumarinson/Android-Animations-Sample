package com.example.androidanimations.fragmenttransitition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.androidanimations.R
import com.example.androidanimations.fragmenttransitition.grid.pager.VPGridFragment
import com.example.androidanimations.fragmenttransitition.grid.single.GridFragment
import kotlinx.android.synthetic.main.fragment_fragment_transition.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentTransitionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        button6.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.TRAN_SHARE_2)) }
        button7.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.TRAN_SHARE_2_ENT_EX)) }
        button8.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.TRAN_SHARE_CUSTOM)) }
        button9.setOnClickListener { openScreen(Fr1Fragment.getInstance(Type.TRAN_SHARE_REMOTE)) }
        button10.setOnClickListener { openScreen(GridFragment()) }
        button11.setOnClickListener { openScreen(VPGridFragment.newInstance(true)) }
        button12.setOnClickListener { openScreen(VPGridFragment.newInstance(false)) }
    }

    private fun openScreen(frag: Fragment) {
        fragmentManager?.beginTransaction()
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.replace(R.id.container, frag, "openFrag")
            ?.addToBackStack(null)
            ?.commit()
    }
}
