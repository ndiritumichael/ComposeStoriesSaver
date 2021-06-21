package com.devmike.storiessaver.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devmike.storiessaver.model.STATUS_TYPE
import com.devmike.storiessaver.model.Status
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.LoadPainter
import java.io.File

@Composable
fun FullScreenStatus(status:Status){
    val modifier = Modifier.fillMaxSize(1f)
    val painter = rememberGlidePainter(request = Uri.fromFile(File(status.path)))

    Text(text = "Image full")
    
    when(status.type){
        STATUS_TYPE.IMAGE ->{
            ShowImage(status,modifier,painter)
        }
        STATUS_TYPE.VIDEO ->{
            
            ShowVideo(status,modifier,painter)
        }
    }
    
    
}

@Composable
fun ShowVideo(status: Status,modifier: Modifier = Modifier,painter:LoadPainter<Any>) {

    Box(modifier = modifier){
Image(painter = painter, contentDescription = "fullScreen Image",modifier = modifier)
    }

}

@Composable
fun ShowImage(status: Status,modifier: Modifier = Modifier,painter: LoadPainter<Any>) {
    Box(modifier = modifier){
        Image(painter = painter, contentDescription = "fullScreen Image",modifier = modifier)
      //
    }

}
