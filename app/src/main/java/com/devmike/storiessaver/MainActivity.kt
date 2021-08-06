package com.devmike.storiessaver

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.tooling.preview.Preview
import com.devmike.storiessaver.ui.theme.StoriesSaverTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import com.devmike.storiessaver.model.Status
import com.devmike.storiessaver.screens.AllScreens
import com.devmike.storiessaver.screens.FullScreenStatus
import com.devmike.storiessaver.screens.components.StatusList
import com.devmike.storiessaver.screens.components.StoryTabRow


import com.devmike.storiessaver.viewmodel.StoriesViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {


    private val storiesViewModel: StoriesViewModel by lazy {
        ViewModelProvider(this).get(StoriesViewModel::class.java)
    }




    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val context = LocalContext.current


            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()) { isGranted ->
                storiesViewModel.updatePermssions(isGranted)
                when (isGranted) {
                    true -> {

                        Log.d("perms","Storage Enabled")

                        storiesViewModel.getFiles()
                    }
                    false-> {
                        Log.d("perms","Storage DisEnabled")
                    }
                }

                
            }

            storiesViewModel.updatePermssions(checkPermission(context))
            var isPermissionenabled = storiesViewModel.isStoragePermissionEnabled.value





            if (isPermissionenabled){
                SaverScreen()
                Log.d("perms","Permissions allowed")


            } else{
             Button(onClick = { launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) },
             ){
                 Text(text = "Click Here To allow Permissions")
             }
            }



        }
    }

   /* private fun askForPermissions(){
        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            when (isGranted) {
                true -> {
                    storiesViewModel.getFiles()
                }
                false-> {

                }
            }

        }

        launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }*/
    private fun checkPermission(context: Context):Boolean{
      return  when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) -> true


            else -> {
                Log.d("perms","Permissions  when  not allowed")


                false}


        }



    }


    @ExperimentalMaterialApi
    @Composable
    fun SaverScreen() {
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .componentRegistry {
                add(VideoFrameFileFetcher(LocalContext.current))
                add(VideoFrameUriFetcher(LocalContext.current))
                add(VideoFrameDecoder(LocalContext.current))
            }
            .build()
        val coroutineScope = rememberCoroutineScope()
        val scaffoldStates = rememberScaffoldState()
        StoriesSaverTheme {
            val navController = rememberNavController()
            val allscreens = AllScreens.values().toList()
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentScreen = AllScreens.fromRoute(backStackEntry.value?.destination?.route)
            Scaffold(
                scaffoldState = scaffoldStates
                ,
                topBar = {
                    if (currentScreen!= AllScreens.FullScreen) {


                        StoryTabRow(
                            allScreens = allscreens,
                            onTabSelected = { screen -> navController.navigate(screen.name) },
                            currentScreen = currentScreen
                        )
                    }

                }, floatingActionButton = {
                    RefreshStatus{
                        storiesViewModel.getFiles()
                        coroutineScope.launch {
                            scaffoldStates.snackbarHostState.showSnackbar(message = "Refreshing...",
                            duration = SnackbarDuration.Short,
                            )
                        }

                    }
                }
            ) { innerpadding ->
                StoryNavHost(

                    navHostController = navController,
                    modifier = Modifier.padding(innerpadding),
                    imageLoader
                )


            }

        }

    }


    @ExperimentalMaterialApi
    @Composable
    private fun StoryNavHost(


        navHostController: NavHostController,
        modifier: Modifier = Modifier,
        imageLoader: ImageLoader
    ) {


        NavHost(
            navController = navHostController,
            startDestination = AllScreens.Images.name,
            modifier = modifier
        ) {

            composable(AllScreens.Images.name) {
                val itemsNumber =  "${storiesViewModel.imageStatus.value.size} Statuses Found"
              Column {
                  Surface() {
                      Text(text = itemsNumber,modifier = Modifier.fillMaxWidth(1f)
                          .padding(top = 4.dp).align(Alignment.CenterHorizontally),
                          textAlign = TextAlign.Center)
                  }
                  Spacer(modifier = Modifier
                      .fillMaxWidth(1f)
                      .height(4.dp))
                  StatusList(navHostController = navHostController,storiesViewModel.imageStatus.value,imageLoader)


              }

            }
            composable(AllScreens.Videos.name) {

                val itemsNumber =  "${storiesViewModel.videoStatus.value.size} Statuses Found"
                Surface() {
                    Text(text = itemsNumber,modifier = Modifier.fillMaxWidth(1f))
                }
                StatusList(
                    navHostController = navHostController,
                    statusList = storiesViewModel.videoStatus.value,
                    imageLoader = imageLoader
                )

            }
            composable(AllScreens.Saved.name) {


            }

            composable(AllScreens.FullScreen.name

          )
            {


                val status = navHostController.previousBackStackEntry?.arguments?.getParcelable<Status>("key")
                
               // Text(text = "${status?.path}")

                if (status != null) {
                    FullScreenStatus(status = status)
                }





/*
                if (path != null) {
                    Text(text = path.toString())
                }*/






            }

        }


    }


    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        StoriesSaverTheme {
            Greeting("Android")
        }
    }

    @Composable
    fun RefreshStatus(onclick : () -> Unit) {
        FloatingActionButton(
            onClick = onclick
                ,
            elevation = FloatingActionButtonDefaults.elevation(6.dp)
        ) {
            Icon(painterResource(id = R.drawable.ic_baseline_refresh_24), contentDescription = " Refresh Icon")


        }


    }
}