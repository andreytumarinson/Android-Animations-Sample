package com.example.androidanimations.transitionframework

import android.media.Image
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_physic.*
import kotlinx.android.synthetic.main.fragment_transition_framework_image.*
import kotlinx.android.synthetic.main.fragment_transition_framework_image.container


class TransitionFrameworkImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transition_framework_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseGroup.visibility = View.GONE
        temp.visibility = View.GONE
        imageGroup.visibility = View.GONE

        container.post {
            TransitionManager.beginDelayedTransition(container, Explode())
            baseGroup.visibility = View.VISIBLE
            temp.visibility = View.VISIBLE
            imageGroup.visibility = View.VISIBLE
        }

        var show = true
        container.setOnClickListener {
            val ts = TransitionSet().apply {
                addTransition(ChangeBounds())
                addTransition(
                    Slide(Gravity.START)
                        .addTarget(textView6)
                        .addTarget(textView9)
                        .addTarget(textView8)
                        .addTarget(textView10)
                )

                addTransition(Slide(Gravity.END).addTarget(temp))
                addTransition(ChangeTransform())
            }

            TransitionManager.beginDelayedTransition(container, ts)

            forecastGroup.visibility = if(show) View.VISIBLE else View.GONE

            temp.visibility = if(show) View.GONE else View.VISIBLE

            sun.layoutParams = sun.layoutParams.apply {
                width = if (show) 220 else 140
                height = if (show) 220 else 150
            }
            sun.rotation = if(show) 90f else 0f

            show = !show
        }


        image1.setOnClickListener(imageClickListener)
        image2.setOnClickListener(imageClickListener)
        image3.setOnClickListener(imageClickListener)
    }


    var largeImage: ImageView ? = null
    private val imageClickListener = View.OnClickListener { v ->
        if(v is ImageView) {
            val trans =  TransitionInflater.from(context).inflateTransition(R.transition.image_transition)
            TransitionManager.beginDelayedTransition(container, trans)
            if(v == largeImage){
                changeImage(v, false)
                largeImage = null
            } else {
                changeImage(v, true)
                largeImage?.let { changeImage(it, false) }
                largeImage = v
            }
        }
    }

    private fun changeImage(view: ImageView, increase: Boolean) {
        view.layoutParams = view.layoutParams.apply {
            height = if(increase) 500 else 280
            width = if(increase) 800 else 280
        }
        view.scaleType = if(increase) ImageView.ScaleType.FIT_XY else ImageView.ScaleType.CENTER_CROP
    }
}
