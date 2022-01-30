package com.algovin373.project.moviecatalog.utils

import com.algovin373.project.moviecatalog.model.SampleDataMovie
import com.algovin373.project.moviecatalog.model.SampleDataTVShow

fun dataMovie() : ArrayList<SampleDataMovie> {
    val arrayDataMovie = ArrayList<SampleDataMovie>()
    arrayDataMovie.add(SampleDataMovie(
        "384018",
        "Fast & Furious Presents: Hobbs & Shaw",
        "2019-08-01",
        "Released",
        "136",
        "6.5",
        "743",
        "A spinoff of The Fate of the Furious, focusing on Johnson's US Diplomatic Security Agent Luke Hobbs forming an unlikely alliance with Statham's Deckard Shaw."
    ))
    return arrayDataMovie
}

fun dataTVShow() : ArrayList<SampleDataTVShow> {
    val arrayDataTVShow = ArrayList<SampleDataTVShow>()
    arrayDataTVShow.add(SampleDataTVShow(
        "79340",
        "The Outpost",
        "2018-07-10",
        "2",
        "20",
        "6.0",
        "34",
        "Talon, the lone survivor of a race called the Blackbloods, sets off to the edge of civilisation to track her family's killers. On her journey she discovers she has supernatural powers which she must learn to harness in order to achieve her goals."
    ))
    return arrayDataTVShow
}