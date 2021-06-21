package com.devmike.storiessaver.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


enum class STATUS_TYPE {
    IMAGE,
    VIDEO
}
@Parcelize
data class Status(val path: String,
                  val type: STATUS_TYPE,
                  val bitmap: Bitmap? = null ):Parcelable{

}