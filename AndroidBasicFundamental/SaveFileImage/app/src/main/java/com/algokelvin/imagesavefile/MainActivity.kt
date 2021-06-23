package com.algokelvin.imagesavefile

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.algokelvin.imagesavefile.ConstFunction.createFotoapparat
import com.algokelvin.imagesavefile.ConstFunction.rotateBitmap
import com.algokelvin.imagesavefile.ConstFunction.saveImage
import com.algokelvin.imagesavefile.ConstFunction.toBitmap
import com.bumptech.glide.Glide
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.selector.firstAvailable
import io.fotoapparat.selector.off
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private lateinit var fotoapparat: Fotoapparat
    private var isFlash = 0
    private var fileImg = 0
    private lateinit var gvBitmap: Bitmap
    private lateinit var strComp: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(hasNoPermissions())
            requestPermission()

        fotoapparat = createFotoapparat(camera_View, isFlash)

        btn_TakePicture.setOnClickListener { takePhoto() }
        btn_ReTakePicture.setOnClickListener { uploadPhotos() }
    }
    private fun hasNoPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun takePhoto() {
        img_scan.visibility = View.GONE
        try {
            groupTakePhoto.visibility = View.GONE
            groupWait.visibility = View.VISIBLE
            val photoResult = fotoapparat.takePicture()

            // Asynchronously converts photo to bitmap and returns the result on the main thread
            photoResult.toPendingResult().whenAvailable { bitmapPhoto ->
                groupWait.visibility = View.GONE
                groupReTakePhoto.visibility = View.VISIBLE

                val encodedImg = bitmapPhoto?.encodedImage
                val theBitmap = encodedImg?.toBitmap()

                val uriImg = saveImage(this, theBitmap, "Camera02", "sample_img$fileImg")

                var rotatedBitmap: Bitmap? = theBitmap
                val deviceRotate = bitmapPhoto?.rotationDegrees
                Log.i("imgBase64", deviceRotate.toString())

                if (deviceRotate == 270) {
                    rotatedBitmap = rotateBitmap(theBitmap!!, 90f)
                }
                gvBitmap = rotatedBitmap!!

                if (isFlash == 1) {
                    fotoapparat.updateConfiguration(
                        UpdateConfiguration(
                            flashMode = firstAvailable(
                                off()
                            )
                        )
                    )
                }

                strComp = Base64.getEncoder().encodeToString(encodedImg)
                Log.i("imgBase64", strComp)
                Glide.with(pictKtp).load(gvBitmap).into(pictKtp)
            }
        }
        catch (e: Exception){
            Log.e("exception", e.message.toString())
            return
        }
    }
    private fun uploadPhotos() {
        groupReTakePhoto.visibility = View.GONE
        groupTakePhoto.visibility = View.VISIBLE
        fileImg++
    }

    override fun onStop() {
        super.onStop()
        fotoapparat.stop()
    }
    override fun onResume() {
        super.onResume()
        fotoapparat.start()
    }
}