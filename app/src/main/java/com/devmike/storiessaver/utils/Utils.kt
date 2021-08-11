package com.devmike.storiessaver.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.request.ImageRequest
import com.devmike.storiessaver.R

object Utils {

    fun getImageLoader(context: Context) =  ImageLoader.Builder(context)
        .componentRegistry {
            add(VideoFrameFileFetcher(context))
            add(VideoFrameUriFetcher(context))
            add(VideoFrameDecoder(context))
        }
        .build()

    fun  requestBuilder(context: Context) = ImageRequest.Builder(context)
        .data("https://www.example.com/image.jpg")
        .crossfade(true)
        .target{

        }
        .build()




 fun getDominantColor(bitmap: Bitmap,defaultColor: Int) = Palette.from(bitmap).generate().getDominantColor(defaultColor)




/*: Int {
     var dominantColor: Int
     Palette.from(bitmap).generate{ palette ->
         palette?.let { notNullPalette ->
             dominantColor = notNullPalette.getDominantColor(defaultColor)
             // dominantColor = notNullPalette.getDominantColor(defaultColor)







         }


     }

 }*/
}