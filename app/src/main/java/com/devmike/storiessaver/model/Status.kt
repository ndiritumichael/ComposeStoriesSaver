package com.devmike.storiessaver.model

import android.graphics.Bitmap

enum class STATUS_TYPE {
    IMAGE,
    VIDEO
}

data class Status(val path: String, val type: STATUS_TYPE,val bitmap: Bitmap? = null ) {

}