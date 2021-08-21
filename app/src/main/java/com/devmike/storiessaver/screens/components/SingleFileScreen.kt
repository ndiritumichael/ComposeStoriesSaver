package com.devmike.storiessaver.screens.components


import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.Coil
import coil.ImageLoader
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.util.CoilUtils
import com.devmike.storiessaver.R
import com.devmike.storiessaver.model.STATUS_TYPE

import com.devmike.storiessaver.model.Status
import com.devmike.storiessaver.utils.Utils
import com.devmike.storiessaver.viewmodel.StoriesViewModel


import java.io.File

object SingleFileScreen {

    @ExperimentalMaterialApi
    @Composable
    fun SingleScreen(
        modifier: Modifier = Modifier,
        status: Status,
        navController: NavController,
        index: Int,
        storiesViewModel: StoriesViewModel = viewModel(),
       context: Context
    ) {

        val type = if (status.type == STATUS_TYPE.IMAGE) 1 else 2

        val route = "fullScreen/${index}/"

        val defaultDominantColor = MaterialTheme.colors.surface
        var dominantColor = remember {
            mutableStateOf(defaultDominantColor)
        }

       val imageLoader = Utils.getImageLoader(LocalContext.current)
       val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(File(status.path))
           .allowConversionToBitmap(true)
          /* .target(
                onSuccess = {
                        drawable ->
                    storiesViewModel.calcDominantColor(drawable){color ->
                        dominantColor.value= color

                        Log.d("dominant","sucesss")


                    }

                }
            )*/

            .build()


val painter = rememberImagePainter( data = File(status.path),
    imageLoader = imageLoader

)
        var colors by remember {

            mutableStateOf((Color.Transparent))
        }


        Card(
            onClick = {
                Log.d("mikewil", status.path)
                val type = if (status.type == STATUS_TYPE.IMAGE) 1 else 2

                val route = "fullScreen/${index}/"

                Log.d("mikewil", route)
                navController.currentBackStackEntry?.arguments = Bundle().apply {
                   putInt("key",index)
                    putInt("type",type)

                }

                navController.navigate("fullscreen")





            },border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            ,
            elevation = 4.dp
            ,



            modifier = modifier
                .padding(top = 8.dp, bottom = 8.dp)
                //.fillMaxWidth(0.5f) using this means it will utilize half of available space
                .height(200.dp)
                //.background(color = dominantColor.value)
        ) {

            Box(
                modifier = modifier.fillMaxSize(),

                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painter,
                    contentDescription = "pictureDetail",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,


                    )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Transparent,
                                    MaterialTheme.colors.surface
                                ),
                                startY = 300f
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    BottomRow(
                        status = status,
                        share = { storiesViewModel.share(status) },
                        delete = { storiesViewModel.delete(status) },
                        save =   { storiesViewModel.save(status) }
                    )
                }


                when (painter.state) {
                     is ImagePainter.State.Loading -> {
                         // Display a circular progress indicator whilst loading
                         CircularProgressIndicator(Modifier.align(Alignment.Center))
                     }
                     is ImagePainter.State.Error-> {
                         // If you wish to display some content if the request
                         Text(text = "Error Loading Image",color = Color.Red,fontSize = 24.sp)
                     }
                     is ImagePainter.State.Success ->{

                     }
                 }
                // Image(painter = , contentDescription = )
            }


        }


    }

    @Composable
    fun BottomRow(
        status: Status,
        modifier: Modifier = Modifier,
        share: (Status) -> Unit,
        delete: (Status) -> Unit,
        save: (Status) -> Unit
    ) {
        Row(
            modifier = modifier

                .fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Filled.Share,
                contentDescription = "share media",
                Modifier
                    .clickable { share(status) }
                    .weight(1f))

            if (status.saved) {
                Icon(imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    Modifier
                        .clickable { delete(status) }
                        .weight(1f))
            } else {

                Icon(painterResource(id = R.drawable.ic_baseline_save_24),
                    contentDescription = "Save",
                    Modifier
                        .clickable { save(status) }
                        .weight(1f))
            }

        }


    }
}


@ExperimentalMaterialApi
@Composable
fun StatusList(
    navHostController: NavHostController,
    statusList: List<Status>,
    context: Context

) {


    // Log.d("Status List", "${viewModel.statusListstate.value}")
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(1f)
            .padding(8.dp)
    ) {

        val statusCount = if (statusList.size % 2 == 0) {
            statusList.size / 2
        } else {
            statusList.size / 2 + 1
        }
        items(statusCount) { index ->
            StatusRow(rowIndex = index, navController = navHostController, statusList = statusList,context = context)


            /* SingleFileScreen.SingleScreen(status = item) {
                 Log.d("Single Item","name :${item.path}")*/

        }

    }
}


@ExperimentalMaterialApi
@Composable
fun StatusRow(
    rowIndex: Int,
    navController: NavHostController,
    statusList: List<Status>,
   context: Context
) {
    Column {
        Row {
            SingleFileScreen.SingleScreen(
                status = statusList[rowIndex * 2],
                modifier = Modifier.weight(1f),
                navController = navController, index = rowIndex * 2,
              context = context


            )
            Spacer(modifier = Modifier.width(16.dp))
            if (statusList.size >= rowIndex * 2 + 2) {

                SingleFileScreen.SingleScreen(
                    status = statusList[rowIndex * 2 + 1],
                    modifier = Modifier.weight(1f),
                    navController = navController,
                    index = rowIndex * 2 + 1,
                    context = context
                )


            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

        }

    }


}

