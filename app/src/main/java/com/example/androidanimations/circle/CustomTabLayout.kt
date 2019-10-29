package com.example.androidanimations.circle

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.tabs.TabLayout

interface OnInterceptTouchListener {
    fun onInterceptTouchEvent(ev: MotionEvent)
}

class CustomTabLayout: TabLayout {

    var onInterceptTouchListener: OnInterceptTouchListener? = null

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        onInterceptTouchListener?.onInterceptTouchEvent(ev)
        return super.onInterceptTouchEvent(ev)
    }
}