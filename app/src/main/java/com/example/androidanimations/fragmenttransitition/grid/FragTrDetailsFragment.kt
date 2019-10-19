package com.example.androidanimations.fragmenttransitition.grid


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import com.example.androidanimations.utils.Item
import kotlinx.android.synthetic.main.fragment_frag_tr_details.*


/**
 * A simple [Fragment] subclass.
 */
class FragTrDetailsFragment : Fragment() {

    companion object {
        private const val EXTRA_ANIMAL_ITEM = "animal_item"
        private const val EXTRA_TRANSITION_NAME = "transition_name"

        fun newInstance(item: Item, transitionName: String): FragTrDetailsFragment {
            return FragTrDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_ANIMAL_ITEM, item)
                    putString(EXTRA_TRANSITION_NAME, transitionName)
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_frag_tr_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable(EXTRA_ANIMAL_ITEM) as Item
        val transitionName = arguments?.getString(EXTRA_TRANSITION_NAME)

        title.text = item.name
        imageView.transitionName = transitionName
        imageView.setImageResource(item.image)

        /*Picasso.with(context)
            .load(item!!.imageUrl)
            .noFade()
            .into(imageView, object : Callback() {
                fun onSuccess() {
                    startPostponedEnterTransition()
                }

                fun onError() {
                    startPostponedEnterTransition()
                }
            })*/

        parentFragment?.startPostponedEnterTransition()
    }
}
