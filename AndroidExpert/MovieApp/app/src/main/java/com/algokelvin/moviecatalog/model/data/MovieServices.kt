package com.algokelvin.moviecatalog.model.data

import androidx.lifecycle.LiveData
import com.algokelvin.moviecatalog.model.entity.DataMovie

interface MovieServices {
    fun movie(): LiveData<List<DataMovie>>
    fun movieNowPlaying(): LiveData<List<DataMovie>>
}