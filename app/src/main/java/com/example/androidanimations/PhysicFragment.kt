package com.example.androidanimations


import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.core.view.GestureDetectorCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_physic.*


/**
 * A simple [Fragment] subclass.
 *
 */
class PhysicFragment : Fragment() {

    lateinit var springXAnimation: SpringAnimation
    lateinit var springYAnimation: SpringAnimation

    lateinit var flingXAnimation: FlingAnimation
    lateinit var flingYAnimation: FlingAnimation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_physic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        springButton.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                springXAnimation = SpringAnimation(springButton, SpringAnimation.X, springButton.x).apply {
                    spring.stiffness = SpringForce.STIFFNESS_MEDIUM
                    spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                }

                springYAnimation = SpringAnimation(springButton, SpringAnimation.Y, springButton.y).apply {
                    spring.stiffness = SpringForce.STIFFNESS_MEDIUM
                    spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                }

                springButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
        

        springButton.setOnTouchListener(object : MoveTouchListener() {
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        // cancel animations so we can grab the view during previous animation
                        springXAnimation.cancel()
                        springYAnimation.cancel()
                    }
                    MotionEvent.ACTION_UP -> {
                        springXAnimation.start()
                        springYAnimation.start()
                    }
                }
                return super.onTouch(view, event)
            }
        })

        container.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    springXAnimation.animateToFinalPosition(event.x)
                    springYAnimation.animateToFinalPosition(event.y)
                }
            }
            true
        }

        flingButton.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val dimension = DisplayMetrics()
                activity?.windowManager?.defaultDisplay?.getMetrics(dimension)

                flingXAnimation = FlingAnimation(flingButton, DynamicAnimation.X).apply {
                    setMinValue(0F)
                    setMaxValue(dimension.widthPixels.toFloat() / 1.4F)
                    friction = 0.9f
                }

                flingYAnimation = FlingAnimation(flingButton, DynamicAnimation.Y).apply {
                    setMinValue(0F)
                    setMaxValue(dimension.heightPixels.toFloat() / 1.3F)
                    friction = 0.9f
                }
                flingButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        val detector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                event1: MotionEvent,
                event2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                textView.text = "x=${event1.rawX}\ny=${event1.rawY}\n" +
                        "x=${event2.rawX}\ny=${event2.rawY}\n" +
                        "velocityX=${event1.rawX}\nvelocityY=${event1.rawY}"

                flingXAnimation.setStartVelocity(velocityX)
                flingYAnimation.setStartVelocity(velocityY)
                try {
                    flingXAnimation.start()
                    flingYAnimation.start()
                } catch (e: IllegalArgumentException) {
                    Log.e("fdf", "IllegalArgumentException on fling", e)
                }
                return false
            }
        })

        flingButton.setOnTouchListener(object : MoveTouchListener() {
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                textView.text = "x=${event.rawX}\ny=${event.rawY}"
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        // cancel animations so we can grab the view during previous animation
                        flingXAnimation.cancel()
                        flingYAnimation.cancel()
                    }
                }
                detector.onTouchEvent(event)
                return super.onTouch(view, event)
            }
        })
    }


    open class MoveTouchListener : View.OnTouchListener {
        var dX = 0f
        var dY = 0f

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // capture the difference between view's top left corner and touch point
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    view.x = event.rawX + dX
                    view.y = event.rawY + dY
                }
            }
            return true
        }
    }
}
