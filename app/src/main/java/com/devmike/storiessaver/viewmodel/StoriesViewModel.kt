package com.devmike.storiessaver.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.devmike.storiessaver.BuildConfig
import com.devmike.storiessaver.model.STATUS_TYPE
import com.devmike.storiessaver.model.Status
import com.devmike.storiessaver.utils.BitmapExtractor
import com.devmike.storiessaver.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class StoriesViewModel(application: Application):AndroidViewModel(application) {
   private val context = application.baseContext
  //  val  destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/statusDownloder/"
    val  destinationPath1 = context.getExternalFilesDir(null)?.getAbsolutePath() + "/StatusDownloder/"
    val isStoragePermissionEnabled  = mutableStateOf(false)
    val videoStatus = mutableStateOf<List<Status>>(listOf())
    val imageStatus = mutableStateOf<List<Status>>(listOf())
    lateinit var validFile: File

    init {
    Log.d("contextcheck", "initialized")
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
fun updatePermssions(boolean: Boolean){
    isStoragePermissionEnabled.value = boolean

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

    fun delete(status: Status) {


    }

    fun save(status: Status) {

        val filename = File(status.path)
     val path = destinationPath1+ filename.name
        Log.d("save",status.path)
        try {
            MediaStore.Images.Media.insertImage(context.getApplicationContext().getContentResolver(), BitmapFactory.decodeFile(status.path),"", "")
            Log.d("copy Error","no error")
        } catch (e : Exception){
            Log.d("copy Error","error is ${e.message}")
        }

    }

    fun save11(status: Status) = viewModelScope.launch(Dispatchers.Default) {
        val bitmap = BitmapFactory.decodeFile(status.path)
        try {
            val fos: OutputStream?
            var imageFile: File = File(status.path)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver: ContentResolver = context.contentResolver
                val contentValues = ContentValues()
                contentValues.put(
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    status.path

                )
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator.toString() + "SavedStatuses"
                )
                val imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
               fos = imageUri?.let { resolver.openOutputStream(it) }
            } else {
                val myDir = File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    ).toString() + File.separator.toString() + "SavedStatuses"
                )
                myDir.mkdirs()
                val filee = File(status.path)

                imageFile = File(myDir,filee.name )
                if (imageFile.exists()) imageFile.delete()
                imageFile.createNewFile()
                if (!imageFile.exists()) {
                    imageFile.mkdirs()
                }
                MediaScannerConnection.scanFile(
                   context,
                    arrayOf(imageFile.toString()),
                    null,
                    null
                )
                fos = FileOutputStream(imageFile)

            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)





        } catch (e: Exception) {

            Log.d("SavingImage", e.toString())
            withContext(Dispatchers.Main) {
               Log.d("savingerror","error saving file")
            }
        }

    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    fun getSavedFiles(){
     val directoryPath =  Environment.DIRECTORY_PICTURES + File.separator.toString() + "SavedStatuses"
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




