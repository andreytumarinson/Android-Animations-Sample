package com.example.androidanimations.transitionframework


import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.transition.TransitionSet.ORDERING_SEQUENTIAL
import kotlinx.android.synthetic.main.fragment_transition_framework_1.*
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_physic.*
import kotlinx.android.synthetic.main.fragment_transition_framework_1.container
import kotlinx.android.synthetic.main.fragment_transition_framework_1.textView


class BasicTransitionFrameworkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transition_framework_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ts = TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(Fade())
        }

        var show = false
        button.setOnClickListener {
            TransitionManager.beginDelayedTransition(container, ts);
            textView.visibility = if(textView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            nestText.visibility = if(textView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            box.layoutParams = box.layoutParams.apply { height = if(show) 150 else 60 }
            show = !show
        }

        var show2 = false
        button2.setOnClickListener {
            TransitionManager.beginDelayedTransition(container);
            nestText3.visibility = if(show2) View.GONE else View.VISIBLE
            show2 = !show2
        }
    }


}
