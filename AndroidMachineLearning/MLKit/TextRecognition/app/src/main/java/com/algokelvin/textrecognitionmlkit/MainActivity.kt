package com.algokelvin.textrecognitionmlkit

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val mImageView: ImageView? = null
    private val mTextButton: Button? = null
    private val mFaceButton: Button? = null
    private val mSelectedImage: Bitmap? = null

    // Max width (portrait mode)
    private val mImageMaxWidth: Int? = null

    // Max height (portrait mode)
    private val mImageMaxHeight: Int? = null

    private val sortedLabels = PriorityQueue<Map.Entry<String, Float>>(RESULTS_TO_SHOW,
        Comparator<Map.Entry<String?, Float?>?> { o1, o2 -> o1?.value?.compareTo(o2?.value!!)!! })

    /* Preallocated buffers for storing image data. */
    private val intValues = IntArray(DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y)

    companion object {
        /**
         * Number of results to show in the UI.
         */
        private const val RESULTS_TO_SHOW = 3

        /**
         * Dimensions of inputs.
         */
        private const val DIM_BATCH_SIZE = 1
        private const val DIM_PIXEL_SIZE = 3
        private const val DIM_IMG_SIZE_X = 224
        private const val DIM_IMG_SIZE_Y = 224
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}