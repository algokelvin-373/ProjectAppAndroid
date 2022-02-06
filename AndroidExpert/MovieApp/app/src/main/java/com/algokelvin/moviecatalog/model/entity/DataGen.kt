package com.algokelvin.moviecatalog.model.entity

abstract class DataGen {
    abstract val id: Int?
    abstract val title: String?
    abstract val poster: String?
    abstract val release: String?
    abstract val background: String?
}