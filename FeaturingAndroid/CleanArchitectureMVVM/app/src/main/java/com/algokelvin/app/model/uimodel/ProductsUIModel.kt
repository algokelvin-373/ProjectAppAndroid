package com.algokelvin.app.model.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsUIModel(var data: List<ProductUIModel> = emptyList()) : Parcelable