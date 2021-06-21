package com.algokelvin.imagesavefile

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.*
import io.fotoapparat.view.CameraView
import java.io.*

object ConstFunction {
    fun Context.createFotoapparat(camera_View: CameraView, isFlash: Int): Fotoapparat {
        val fotoapparat = Fotoapparat(context = this, view = camera_View, scaleType = ScaleType.CenterCrop, lensPosition = back(),
            logger = loggers(
                logcat()
            ),
            cameraErrorCallback = { error ->
                println("Recorder errors: $error")
            }
        )

        fotoapparat.updateConfiguration(
            UpdateConfiguration(
                flashMode = if (isFlash == 1) {
                    firstAvailable(
                        torch(),
                        off()
                    )
                } else {
                    off()
                },
                previewResolution  = firstAvailable(
                    wideRatio(highestResolution()),
                    standardRatio(highestResolution())
                ),
                exposureCompensation = highestExposure(),
                previewFpsRange = highestFps()
            )
        )

        return fotoapparat
    }
    fun ByteArray.toBitmap(): Bitmap? {
        val arrayInputStream = ByteArrayInputStream(this)
        return BitmapFactory.decodeStream(arrayInputStream)
    }
    fun rotateBitmap(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }
    fun saveImage(context: Context, bitmap: Bitmap?, folderName: String, fileName: String): Uri? {
        val fos: OutputStream?
        var imageFile: File?
        var imageUri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.i("liveness", "if save image")
            val resolver = context.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + folderName)
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = resolver.openOutputStream(imageUri!!)
        } else {
            Log.i("liveness", "else save image")
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + folderName
            imageFile = File(imagesDir)
            if (!imageFile.exists()) {
                imageFile.mkdir()
            }
            imageFile = File(imagesDir, "$fileName.jpeg")
            fos = FileOutputStream(imageFile)
        }
        bitmap?.compress(Bitmap.CompressFormat.PNG, 90, fos)
        fos?.flush()
        fos?.close()
        /*if (imageFile != null) // pre Q
        {
            MediaScannerConnection.scanFile(context, arrayOf(imageFile.toString()), null, null)
            imageUri = Uri.fromFile(imageFile)
        }*/
        return imageUri
    }
}