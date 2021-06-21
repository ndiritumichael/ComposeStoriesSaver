package com.devmike.storiessaver.screens

import com.devmike.storiessaver.R

enum class AllScreens(val icon: Int){
    Images(R.drawable.ic_baseline_image_24),
    Videos(R.drawable.ic_baseline_videocam_24),
    Saved(R.drawable.ic_baseline_save_24),
    NoTab(R.drawable.exo_icon_fullscreen_enter);





    companion object {
        fun fromRoute(route: String?): AllScreens =
            when (route?.substringBefore("/")) {
                Images.name -> Images
                Videos.name -> Videos
                Saved.name -> Saved
                "fullScreen"-> NoTab
                null -> Images
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }

}