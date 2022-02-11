package com.algokelvin.moviecatalog.model.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.algokelvin.moviecatalog.model.entity.DataMovie

class RemoteMovieFactory: MovieServices {

    val movie = MutableLiveData<List<DataMovie>>()
    val movieNowPlaying = MutableLiveData<List<DataMovie>>()

    override fun movie(): LiveData<List<DataMovie>> {
        return movie
    }

    override fun movieNowPlaying(): LiveData<List<DataMovie>> {
        return movieNowPlaying
    }

}