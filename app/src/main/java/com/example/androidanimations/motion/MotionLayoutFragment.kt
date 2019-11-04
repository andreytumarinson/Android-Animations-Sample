package com.example.androidanimations.motion


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidanimations.R
import com.example.androidanimations.motion.fragments.MotionFragmentsActivity
import kotlinx.android.synthetic.main.fragment_motion_layout.*

class MotionLayoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_motion_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSimple.setOnClickListener { startActivity(Intent(context, MotionActivity::class.java)) }
        buttonTutorial.setOnClickListener { startActivity(Intent(context, MotionPagerActivity::class.java)) }
        buttonCollapse.setOnClickListener { startActivity(Intent(context, CollapseToolbarActivity::class.java)) }
        buttonVan.setOnClickListener { startActivity(Intent(context, VanGoghActivity::class.java)) }
        buttonMi.setOnClickListener { startActivity(Intent(context, MiActivity::class.java)) }
        buttonYoutube.setOnClickListener { startActivity(Intent(context, YouTubeActivity::class.java)) }
        buttonFragment.setOnClickListener { startActivity(Intent(context, MotionFragmentsActivity::class.java)) }
    }
}
