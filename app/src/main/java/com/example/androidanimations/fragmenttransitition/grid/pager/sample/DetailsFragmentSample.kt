package com.example.androidanimations.fragmenttransitition.grid.pager.sample


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_frag_tr_details_sample.*


/**
 * A simple [Fragment] subclass.
 */
class DetailsFragmentSample : Fragment() {

    companion object {
        private const val EXTRA_ITEM = "animal_item"
        private const val EXTRA_TRANSITION_NAME = "transition_name"

        fun newInstance(item: Item, transitionName: String): DetailsFragmentSample {
            return DetailsFragmentSample().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_ITEM, item)
                    putString(EXTRA_TRANSITION_NAME, transitionName)
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_frag_tr_details_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable(EXTRA_ITEM) as Item
        val transitionName = arguments?.getString(EXTRA_TRANSITION_NAME)

        title.text = item.name
        imageView.transitionName = transitionName
        imageView.setImageResource(item.image as Int)

        parentFragment?.startPostponedEnterTransition()
    }
}
