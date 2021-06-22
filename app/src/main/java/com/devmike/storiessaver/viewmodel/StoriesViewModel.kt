package com.devmike.storiessaver.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.devmike.storiessaver.BuildConfig
import com.devmike.storiessaver.model.STATUS_TYPE
import com.devmike.storiessaver.model.Status
import com.devmike.storiessaver.utils.BitmapExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class StoriesViewModel(application: Application):AndroidViewModel(application) {
   private val context = getApplication<Application>().applicationContext

     val statusListstate = mutableStateOf<List<Status>>(listOf())
    val videoStatus = mutableStateOf<List<Status>>(listOf())
    val imageStatus = mutableStateOf<List<Status>>(listOf())
    lateinit var validFile: File
   //  var bitMap : Bitmap? = null

/* val statusList: <List<Status>>
        get() = _statusList;*/
    init {
    if (context == null) {
        Log.d("contextcheck", "context null")
    }
    val mainPath: String? = context.getExternalFilesDir(null)?.absolutePath
    if (mainPath != null) {
        val extraPortion = ("Android/data/" + BuildConfig.APPLICATION_ID
                + File.separator + "files")
        val validPath = mainPath.replace(extraPortion, "")
        val statusPath = validPath + "FMWhatsApp/Media/.Statuses"
        validFile = File(statusPath)
        getFiles()


    }
}


    fun getFiles(){
      //  var bitMap : Bitmap? = null

        Log.d("timber","button clicked")



        // Log.d("Status Listfile", "${validFile.listFiles().size}")
        if (validFile.listFiles() != null) {
            val files: MutableList<File> = validFile.listFiles().toMutableList()

            //files.filter { it.extension == "jpg" }
            files.sortByDescending { it.lastModified() }
            val statusList: MutableList<Status> = mutableListOf()
            files.iterator().forEach {

                var extension: STATUS_TYPE? = null;
                if (it.extension == "jpg") {
                    extension = STATUS_TYPE.IMAGE;
                } else if (it.extension == "mp4") {
                    extension = STATUS_TYPE.VIDEO



                  //    bitMap =

                  //  = BitmapExtractor(getApplication()).extractBitmap(it)


                }
                if (extension != null) {

                    statusList.add(
                        Status(
                            it.absolutePath,
                            extension,


                        )
                    );

                    Log.d("adding","${statusList}")
                }

            }
            videoStatus.value = statusList.filterIndexed { index, status ->
                status.type == STATUS_TYPE.VIDEO
            }
            imageStatus.value = statusList.filterIndexed { index, status ->
                status.type == STATUS_TYPE.IMAGE
            }


        }
        else {
            println("Failing")
        }
    }

    fun share(status: Status) {
        Log.d("share", "Share Button Clicked ${status.path}")

    }

    /*fun extractBitmap(file:File):Bitmap?{
       try {
           if (file.extension == "mp4"){

               val retriever = MediaMetadataRetriever()
               retriever.setDataSource(context, Uri.fromFile(file))


               val bitmap = retriever.getFrameAtIndex(2000000)
*//*
        try {
            ThumbnailUtils.createVideoThumbnail( file,MediaStore.Video.Thumbnails. ,null)

            }*//*
               return  bitmap

           }
       } catch (e : Exception){
           Log.d("errorconvertor","${e.message}")
       }
        return  null


    }*/

    }




