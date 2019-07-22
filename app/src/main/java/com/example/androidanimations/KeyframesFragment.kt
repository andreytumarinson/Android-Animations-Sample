package com.example.androidanimations


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.facebook.keyframes.KeyframesDrawableBuilder
import com.facebook.keyframes.deserializers.KFImageDeserializer
import kotlinx.android.synthetic.main.fragment_keyframes.*

class KeyframesFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keyframes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stream = resources.assets.open("keyframes.json")
        val kfImage = KFImageDeserializer.deserialize(stream)

        val kfDrawable = KeyframesDrawableBuilder().withImage(kfImage).build()
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        imageView.setImageDrawable(kfDrawable)
        imageView.imageAlpha = 0

        kfDrawable.startAnimation()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                kfDrawable.stopAnimation()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                kfDrawable.startAnimation()
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                kfDrawable.seekToProgress(progress.toFloat() / seekBar.max)
            }
        })
    }

}
