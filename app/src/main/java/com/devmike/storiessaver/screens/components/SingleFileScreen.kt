
package com.devmike.storiessaver.screens.components



import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.devmike.storiessaver.model.STATUS_TYPE
import com.devmike.storiessaver.model.Status
import com.devmike.storiessaver.viewmodel.StoriesViewModel

import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState
import kotlinx.coroutines.delay
import java.io.File

object SingleFileScreen{
  
    @ExperimentalMaterialApi
    @Composable
    fun SingleScreen(modifier: Modifier = Modifier,status: Status,navController: NavController){
      val glider = rememberGlidePainter(request = Uri.fromFile(File(status.path)) )


        // tried using coil painter unfortunately it can't get video previews

        /*val painter = rememberCoilPainter(request = when(status.type){
         STATUS_TYPE.VIDEO ->{
             status.bitmap
         }
            STATUS_TYPE.IMAGE -> File(status.path)

        })*/

        Card(onClick = {
           Log.d("mikewil",status.path)

            val route = "fullScreen/${status.path}"

            Log.d("mikewil",route)


            //navController.navigate()

        },

        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)


            //.fillMaxWidth(0.5f)
            .height(300.dp)) {
            Box(modifier = modifier.background(color = Color.LightGray)
                ,
                contentAlignment = Alignment.Center){

                Image(painter = glider,
                    contentDescription ="pictureDetail",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
               /* when (painter.loadState) {
                    is ImageLoadState.Loading -> {
                        // Display a circular progress indicator whilst loading
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                    is ImageLoadState.Error -> {
                        // If you wish to display some content if the request
                        Text(text = "Error Loading Image",color = Color.Red,fontSize = 24.sp)
                    }
                    else -> {}
                }*/
               // Image(painter = , contentDescription = )
            }
            

        }



    }
}




@ExperimentalMaterialApi
@Composable
fun StatusList(navHostController: NavHostController,
statusList : List<Status>

) {


   // Log.d("Status List", "${viewModel.statusListstate.value}")
    LazyColumn(modifier = Modifier
        .fillMaxHeight(1f)
        .padding(8.dp)) {

        val statusCount = if (statusList.size% 2 ==0){
            statusList.size /2
        } else{ statusList.size/2 + 1}
        items(statusCount){ index ->
            StatusRow(rowIndex = index , navController = navHostController, statusList = statusList)



           /* SingleFileScreen.SingleScreen(status = item) {
                Log.d("Single Item","name :${item.path}")*/

            }

        }
    }



@ExperimentalMaterialApi
@Composable
fun StatusRow(rowIndex: Int,
navController: NavHostController,
statusList: List<Status>){
Column {
Row {
   SingleFileScreen.SingleScreen(status = statusList[rowIndex * 2],
   modifier = Modifier.weight(1f),
   navController = navController)
    Spacer(modifier = Modifier.width(16.dp))
if (statusList.size>= rowIndex* 2 +2){

    SingleFileScreen.SingleScreen(
        status = statusList[rowIndex *2 + 1],
        modifier = Modifier.weight(1f),
        navController = navController
    )


}
    else {
    Spacer(modifier = Modifier.weight(1f))
    }

}

}


}