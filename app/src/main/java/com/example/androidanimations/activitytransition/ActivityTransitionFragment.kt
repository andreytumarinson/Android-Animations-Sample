package com.example.androidanimations.activitytransition

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import com.example.androidanimations.activitytransition.grid.pager.VPGridActivity
import com.example.androidanimations.activitytransition.grid.pager.ViewPagerActivity
import com.example.androidanimations.activitytransition.grid.single.GridActivity
import kotlinx.android.synthetic.main.fragment_activity_transition.*
import kotlin.reflect.KClass

class ActivityTransitionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_activity_transition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener { openScreen(ATActivity1::class, Type.NO) }
        button1.setOnClickListener { openScreen(ATActivity1::class,Type.ENT_EX_DEF) }
        button2.setOnClickListener { openScreen(ATActivity1::class,Type.CUST_ANIM) }
        button3.setOnClickListener { openScreen(ATActivity1::class,Type.TRAN_ENT_EX) }
        button4.setOnClickListener { openScreen(ATActivity1::class,Type.TRAN_SHARE) }
        button5.setOnClickListener { openScreen(ATActivity1::class,Type.TRAN_SHARE_ENT_EX) }
        button6.setOnClickListener { openScreen(ATActivity1::class,Type.TRAN_SHARE_2) }
        button7.setOnClickListener { openScreen(ATActivity1::class,Type.TRAN_SHARE_2_ENT_EX) }
        button8.setOnClickListener { openScreen(ATActivity1::class,Type.TRAN_SHARE_CUSTOM) }
        button9.setOnClickListener { openScreen(ATActivity1::class,Type.TRAN_SHARE_REMOTE) }
        button10.setOnClickListener { openScreen(GridActivity::class) }
        button11.setOnClickListener { openScreen(VPGridActivity::class, isLocal = true) }
        button12.setOnClickListener { openScreen(VPGridActivity::class, isLocal = false) }
    }

    private fun <T : Any> openScreen(cls: KClass<T>, type: Type? = null, isLocal: Boolean? = null) {
        startActivity(Intent(context, cls.java).apply {
            type?.let { putExtra(ATActivity1.TYPE, it) }
            isLocal?.let { putExtra(VPGridActivity.IS_LOCAL, it) }
        })
    }
 }
