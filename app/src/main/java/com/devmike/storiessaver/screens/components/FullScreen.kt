package com.devmike.storiessaver.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import com.devmike.storiessaver.model.Status
import com.devmike.storiessaver.screens.components.SingleFileScreen
import com.devmike.storiessaver.utils.Utils
import com.devmike.storiessaver.viewmodel.StoriesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.io.File

@ExperimentalPagerApi
@Composable
fun FullScreenStatus(statuses: List<Status>, context: Context, index: Int){
    val imageLoader = Utils.getImageLoader(context)
    val pagerState = rememberPagerState(pageCount = statuses.size,initialPage = index)
    val scope = rememberCoroutineScope()
    val modifier = Modifier.fillMaxSize(1f)




HorizontalPager(state = pagerState) {pager ->
    val  status = statuses[pager]
    val painter = rememberImagePainter(data = Uri.fromFile(File(status.path)),
        builder = {
            crossfade(true)
        },
        imageLoader = imageLoader,
    )

    ShowImage(status = status, painter = painter,modifier = modifier)

}


    
}


@Composable
fun ShowImage(status: Status,modifier: Modifier = Modifier,painter:ImagePainter,storiesViewModel : StoriesViewModel = viewModel()) {
    Box(modifier = modifier){
        Image(painter = painter, contentDescription = "fullScreen Image",modifier = modifier)
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
       Box(modifier= modifier) {
           SingleFileScreen.BottomRow(
               modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
               status = status,
               share = { storiesViewModel.share(status) },
               delete = { storiesViewModel.delete(status) }) { storiesViewModel.save(status) }

       }
      //
    }

}
