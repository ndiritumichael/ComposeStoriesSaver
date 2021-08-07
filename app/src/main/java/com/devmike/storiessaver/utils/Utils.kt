package com.devmike.storiessaver.utils

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher

object Utils {

    fun getImageLoader(context: Context) =  ImageLoader.Builder(context)
        .componentRegistry {
            add(VideoFrameFileFetcher(context))
            add(VideoFrameUriFetcher(context))
            add(VideoFrameDecoder(context))
        }
        .build()





}