package com.example.androidanimations


import android.animation.LayoutTransition
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_layout_changes.*

class LayoutChangesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout_changes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        container.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        ll3.layoutTransition.setAnimateParentHierarchy(false)

        var show = false
        button.setOnClickListener {
            textView.visibility = if(textView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            nestText.visibility = if(textView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            box.layoutParams = box.layoutParams.apply { height = if(show) 150 else 60 }
            show = !show
        }

        var show2 = false
        button2.setOnClickListener {
            nestText2.visibility = if(show2) View.GONE else View.VISIBLE
            show2 = !show2
        }

        var show3 = false
        button3.setOnClickListener {
            nestText3.visibility = if(show3) View.GONE else View.VISIBLE
            show3 = !show3
        }
    }


}
