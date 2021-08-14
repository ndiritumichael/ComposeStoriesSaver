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
                  val bitmap: Bitmap? = null ,
val saved : Boolean = false,
 ):Parcelable{

     companion object{
         fun getStatus() = listOf<Status>(

        Status(path="/storage/emulated/0/FMWhatsApp/Media/.Statuses/8743d313869e468c92eec950a93cc124.jpg, type= STATUS_TYPE.IMAGE, bitmap=null, saved=false), Status(path=/storage/emulated/0/FMWhatsApp/Media/.Statuses/9b0ef68d9dff45429586e35bc992d9fa.jpg", type= STATUS_TYPE.IMAGE, bitmap=null, saved=false),
         Status(path="/storage/emulated/0/FMWhatsApp/Media/.Statuses/8743d313869e468c92eec950a93cc124.jpg", type= STATUS_TYPE.IMAGE, bitmap=null, saved=false),
             Status(path="/storage/emulated/0/FMWhatsApp/Media/.Statuses/9b0ef68d9dff45429586e35bc992d9fa.jpg", type= STATUS_TYPE.IMAGE, bitmap=null, saved=false),

        Status(path="/storage/emulated/0/FMWhatsApp/Media/.Statuses/8743d313869e468c92eec950a93cc124.jpg", type= STATUS_TYPE.IMAGE, bitmap=null, saved=false),
             Status(path="/storage/emulated/0/FMWhatsApp/Media/.Statuses/9b0ef68d9dff45429586e35bc992d9fa.jpg", type= STATUS_TYPE.IMAGE, bitmap=null, saved=false),
             Status(path="/storage/emulated/0/FMWhatsApp/Media/.Statuses/80ef05e0df604264a86510d6f8dd2d70.mp4", type= STATUS_TYPE.VIDEO, bitmap=null, saved=false),
             Status(path="/storage/emulated/0/FMWhatsApp/Media/.Statuses/5e794ead54ee41cda235554b884c501a.mp4", type= STATUS_TYPE.VIDEO, bitmap=null, saved=false)




         )
     }

}

