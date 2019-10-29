package com.example.androidanimations.circle


import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_ripple_custom.*


class RippleCustomFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ripple_custom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*view444.background =(RippleDrawable(
            ColorStateList.valueOf(resources.getColor(R.color.tabColor1)),
            ShapeDrawable(RoundRectShape())*//*ShapeDrawable(ArcShape(0f, 90f))*//*,
            resources.getDrawable(R.drawable.ic_menu_camera)*//*ShapeDrawable(ArcShape(0f, 90f))*//*null))*/
    }
}
