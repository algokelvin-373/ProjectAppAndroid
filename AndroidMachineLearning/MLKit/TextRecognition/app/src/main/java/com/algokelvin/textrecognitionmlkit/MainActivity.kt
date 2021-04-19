package com.algokelvin.textrecognitionmlkit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.max

class MainActivity : AppCompatActivity(), OnItemSelectedListener {
    private lateinit var mSelectedImage: Bitmap
    private var mImageMaxWidth: Int? = null
    private var mImageMaxHeight: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_text.setOnClickListener {
            runTextRecognition()
        }

        val dropdown = findViewById<Spinner>(R.id.spinner)
        val items = arrayOf("Test Image 1 (Text)", "Car 1", "Car 2")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = this
    }

    private fun runTextRecognition() {
        val image = InputImage.fromBitmap(mSelectedImage, 0)
        val recognizer = TextRecognition.getClient()
        button_text.isEnabled = false
        recognizer.process(image)
            .addOnSuccessListener { texts ->
                button_text.isEnabled = true
                Log.i("textRecognition-text", texts.text)
                processTextRecognitionResult(texts)
            }
            .addOnFailureListener { e -> // Task failed with an exception
                button_text.isEnabled = true
                e.printStackTrace()
            }
    }

    private fun processTextRecognitionResult(texts: Text) {
        val blocks = texts.textBlocks
        if (blocks.size == 0) {
            Toast.makeText(this, "No Text Found", Toast.LENGTH_SHORT).show()
            return
        }
        graphic_overlay.clear()
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            Log.i("textRecognition-blocks", blocks[i].lines.toString())
            for (j in lines.indices) {
                Log.i("textRecognition-lines", lines[j].elements.toString())
                val elements = lines[j].elements
                for (k in elements.indices) {
                    Log.i("textRecognition-element", elements[k].text)
                    val textGraphic: GraphicOverlay.Graphic = TextGraphic(graphic_overlay, elements[k])
                    graphic_overlay.add(textGraphic)
                }
            }
        }
    }

    private fun getImageMaxWidth(): Int? {
        if (mImageMaxWidth == null) {
            mImageMaxWidth = image_view.width
        }
        return mImageMaxWidth
    }

    private fun getImageMaxHeight(): Int? {
        if (mImageMaxHeight == null) {
            mImageMaxHeight = image_view.height
        }
        return mImageMaxHeight
    }

    private fun getBitmapFromAsset(context: Context, filePath: String): Bitmap {
        val assetManager = context.assets
        val inputStream = assetManager.open(filePath)
        return BitmapFactory.decodeStream(inputStream)
    }

    // Gets the targeted width / height.
    private fun getTargetedWidthHeight(): Pair<Int?, Int?> {
        val targetWidth = getImageMaxWidth()
        val targetHeight = getImageMaxHeight()
        return Pair(targetWidth, targetHeight)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //Do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        graphic_overlay.clear()
        when (position) {
            0 -> mSelectedImage = getBitmapFromAsset(this, "Please_walk_on_the_grass.jpg")
            1 -> mSelectedImage = getBitmapFromAsset(this, "Car01.jpg")
            2 -> mSelectedImage = getBitmapFromAsset(this, "Car02.jpg")
        }
        // Get the dimensions of the View
        val targetedSize: Pair<Int?, Int?> = getTargetedWidthHeight()
        val targetWidth = targetedSize.first
        val maxHeight = targetedSize.second

        // Determine how much to scale down the image
        if (targetWidth != null && maxHeight != null) {
            val scaleFactor = max(mSelectedImage.width.toFloat() / targetWidth.toFloat(), mSelectedImage.height.toFloat() / maxHeight.toFloat())
            val resizedBitmap = Bitmap.createScaledBitmap(mSelectedImage, (mSelectedImage.width / scaleFactor).toInt(), (mSelectedImage.height / scaleFactor).toInt(), true)
            image_view.setImageBitmap(resizedBitmap)
            mSelectedImage = resizedBitmap
        }
    }
}