package com.algokelvin.recordcallwa.recorder

import android.content.Context
import java.io.File

object CacheFileProvider {
    fun provideCacheFile(context: Context, fileName: String) = File(getExternalCacheDirectory(context), fileName)

    fun getExternalCacheDirectory(context: Context): File {
        return File(context.getExternalFilesDir(null), "recordings").also { folder ->
            if (folder.exists().not()) {
                folder.mkdirs()
            }
        }
    }
}