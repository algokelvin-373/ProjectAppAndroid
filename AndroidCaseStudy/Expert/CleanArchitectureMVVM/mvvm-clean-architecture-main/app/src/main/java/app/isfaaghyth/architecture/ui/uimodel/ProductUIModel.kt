package app.isfaaghyth.architecture.ui.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUIModel(
    var id: Int = 0,
    var name: String = "",
    var price: Int = 0
) : Parcelable