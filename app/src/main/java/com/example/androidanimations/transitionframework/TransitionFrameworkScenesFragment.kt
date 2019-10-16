package com.example.androidanimations.transitionframework


import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet

import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_tf_scenes_1.*
import kotlinx.android.synthetic.main.fragment_transition_framework_scenes.*

/**
 * A simple [Fragment] subclass.
 */
class TransitionFrameworkScenesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transition_framework_scenes, container, false)
        // constrain set variant
        //return inflater.inflate(R.layout.fragment_tf_scenes_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scene1: Scene = Scene.getSceneForLayout(scene_root, R.layout.fragment_tf_scenes_1, context)
        val scene2: Scene = Scene.getSceneForLayout(scene_root, R.layout.fragment_tf_scenes_2, context)

        var show = true

        scene_root.setOnClickListener {
            if(show) TransitionManager.go(scene2)
            else TransitionManager.go(scene1)
            show = !show
        }

        // constrain set variant
        /*container.setOnClickListener {
            val constraintSet = ConstraintSet()
            constraintSet.clone(context, if(show) R.layout.fragment_tf_scenes_2 else R.layout.fragment_tf_scenes_1)

            TransitionManager.beginDelayedTransition(container)
            constraintSet.applyTo(container)

            show = !show
        }*/
    }

}
