package com.devmike.storiessaver.screens

import com.devmike.storiessaver.R

enum class AllScreens(val icon: Int?){
    Images(R.drawable.ic_baseline_image_24),
    Videos(R.drawable.ic_baseline_videocam_24),
    Saved(R.drawable.ic_baseline_save_24),
    FullScreen(null);
  //  NoTab();





    companion object {
        fun fromRoute(route: String?): AllScreens =
            when (route?.substringBefore("/")) {
                Images.name -> Images
                Videos.name -> Videos
                Saved.name -> Saved
                FullScreen.name -> FullScreen
                null -> Images
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }

}