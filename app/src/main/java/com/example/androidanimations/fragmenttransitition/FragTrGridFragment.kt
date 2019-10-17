package com.example.androidanimations.fragmenttransitition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidanimations.MainActivity
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import com.example.androidanimations.utils.sampleGridData
import kotlinx.android.synthetic.main.fragment_frag_tr_grid.*


class FragTrGridFragment : Fragment(), OnItemClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_tr_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GridAdapter(sampleGridData, this)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                    // Locate the ViewHolder for the clicked position.
                    val selectedViewHolder = recyclerView.findViewHolderForAdapterPosition(MainActivity.currentPosition)
                    if (selectedViewHolder?.itemView == null) {
                        return
                    }

                    Log.e("dd", "setExitSharedElementCallback ${MainActivity.currentPosition} ${selectedViewHolder.itemView.findViewById<View>(R.id.image)}")
                    // Map the first shared element name to the child ImageView.
                    sharedElements[/*"f_"+MainActivity.currentPosition*/names[0]] = selectedViewHolder.itemView.findViewById(R.id.image)
                }
            })

        scrollToPosition()
    }

    override fun onItemClicked(position: Int, item: Item, imageView: ImageView) {

        //exitTransition = Explode()
        MainActivity.currentPosition = position

        val fragment = ViewPagerFragment.newInstance(position, sampleGridData)

        //FragTrDetailsFragment.newInstance(item, imageView.transitionName)


      //  fragment.enterTransition = (Slide(Gravity.BOTTOM))

           // TransitionSet()
            //.addTransition(Fade().excludeTarget(title, true).excludeTarget(details, true))
           // .addTransition(Slide(Gravity.BOTTOM)/*.addTarget(title).addTarget(details)*/)
// The 'view' is the card view that was clicked to initiate the transition.


        //(fragment.exitTransition as Transition).excludeTarget(view, true)

        //parentFragment?.childFragmentManager
                fragmentManager
            ?.beginTransaction()
            ?.setReorderingAllowed(true)
            ?.addSharedElement(imageView, imageView.transitionName)
            ?.addToBackStack(null)
            ?.replace(R.id.container, fragment)
            ?.commit()

       // (parentFragment as FragmentTransitionFragment).openScreen3(item)
    }


    private fun scrollToPosition() {
        recyclerView.addOnLayoutChangeListener(object : OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                recyclerView.removeOnLayoutChangeListener(this)
                val layoutManager = recyclerView.layoutManager
                val viewAtPosition = layoutManager!!.findViewByPosition(MainActivity.currentPosition)
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)
                ) {
                    recyclerView.post { layoutManager.scrollToPosition(MainActivity.currentPosition) }
                }
            }
        })
    }

}
