package com.example.androidanimations.transitionframework


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.*
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_transition_framework.*


class TransitionFrameworkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transition_framework, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_base -> openScreen(BasicTransitionFrameworkFragment())
                R.id.action_image -> openScreen(TransitionFrameworkImageFragment())
                R.id.action_scenes -> openScreen(TransitionFrameworkScenesFragment())
            }
            true
        }
        bottomNav.selectedItemId = R.id.action_base
    }

    private fun openScreen(frag: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.frame, frag, "openFrag")
            .commit()
    }

}
