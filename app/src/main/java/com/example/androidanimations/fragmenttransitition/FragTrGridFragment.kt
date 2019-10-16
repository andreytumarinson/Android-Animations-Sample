package com.example.androidanimations.fragmenttransitition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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
    }

    override fun onItemClicked(position: Int, item: Item, imageView: ImageView) {

        //exitTransition = Explode()


        val fragment = FragTrDetailsFragment.newInstance(item, imageView.transitionName)
      //  fragment.enterTransition = (Slide(Gravity.BOTTOM))

           // TransitionSet()
            //.addTransition(Fade().excludeTarget(title, true).excludeTarget(details, true))
           // .addTransition(Slide(Gravity.BOTTOM)/*.addTarget(title).addTarget(details)*/)


        //parentFragment?.childFragmentManager
                fragmentManager
            ?.beginTransaction()
            //?.setReorderingAllowed(true)
            ?.addSharedElement(imageView, imageView.transitionName)
            ?.addToBackStack(null)
            ?.replace(R.id.container, fragment)
            ?.commit()

       // (parentFragment as FragmentTransitionFragment).openScreen3(item)
    }
}
