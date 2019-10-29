package com.example.androidanimations.circle


import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.ArcShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidanimations.R
import kotlinx.android.synthetic.main.fragment_ripple_default.*


class RippleDefaultFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ripple_default, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*button7.setOnClickListener { startActivity(Intent(context, RevealActivity::class.java)) }
        button8.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.addToBackStack("ddd")
                    ?.replace(R.id.container, RevealSamplesFragment())
                    ?.commit()
        }
        button9.setOnClickListener { showDialog() }*/

    }

    /*private fun showDialog() {
        val dialogView = View.inflate(activity, R.layout.dialog_view, null)

        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
                .setCancelable(false)


        val dialog = builder.create()
        dialog.setOnShowListener { revealShow(dialogView, true, null) }
        dialogView.btn_cancel.setOnClickListener { revealShow(dialogView, false, dialog) }
        dialogView.btn_save.setOnClickListener { revealShow(dialogView, false, dialog) }

        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }*/

    /*private fun revealShow(rootView: View, reveal: Boolean, dialog: AlertDialog?) {
        val view = rootView.reveal_view
        val w = view.width
        val h = view.height
        val maxRadius = sqrt((w * w / 4 + h * h / 4).toDouble()).toFloat()

        if (reveal) {
            val revealAnimator = ViewAnimationUtils.createCircularReveal(view,
                    w / 2, h / 2, 0f, maxRadius)

            view.visibility = View.VISIBLE
            revealAnimator.start()

        } else {

            val anim = ViewAnimationUtils.createCircularReveal(view, w / 2, h / 2, maxRadius, 0f)

            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    dialog!!.dismiss()
                    view.visibility = View.INVISIBLE

                }
            })

            anim.start()
        }

    }*/
}
