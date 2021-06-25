package com.devmike.storiessaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.devmike.storiessaver.ui.theme.StoriesSaverTheme
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.devmike.storiessaver.model.STATUS_TYPE
import com.devmike.storiessaver.model.Status
import com.devmike.storiessaver.screens.AllScreens
import com.devmike.storiessaver.screens.FullScreenStatus
import com.devmike.storiessaver.screens.components.StatusList


import com.devmike.storiessaver.screens.components.StoryTabRow
import com.devmike.storiessaver.viewmodel.StoriesViewModel
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : ComponentActivity() {

    private val storiesViewModel: StoriesViewModel by lazy {
        ViewModelProvider(this).get(StoriesViewModel::class.java)
    }


    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            SaverScreen()

        }
    }


    @ExperimentalMaterialApi
    @Composable
    fun SaverScreen() {
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
                    StoryTabRow(
                        allScreens = allscreens,
                        onTabSelected = { screen -> navController.navigate(screen.name) },
                        currentScreen = currentScreen
                    )
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
                    modifier = Modifier.padding(innerpadding)
                )


            }

        }

    }


    @ExperimentalMaterialApi
    @Composable
    private fun StoryNavHost(navHostController: NavHostController, modifier: Modifier = Modifier) {


        NavHost(
            navController = navHostController,
            startDestination = AllScreens.Images.name,
            modifier = modifier
        ) {

            composable(AllScreens.Images.name) {
                val itemsNumber =  "${storiesViewModel.imageStatus.value.size} Statuses Found"
                Surface() {
                    Text(text = itemsNumber,modifier = Modifier.fillMaxWidth(1f))
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(16.dp))
                StatusList(navHostController = navHostController,storiesViewModel.imageStatus.value)


            }
            composable(AllScreens.Videos.name) {

                val itemsNumber =  "${storiesViewModel.videoStatus.value.size} Statuses Found"
                Surface() {
                    Text(text = itemsNumber,modifier = Modifier.fillMaxWidth(1f))
                }
                StatusList(navHostController = navHostController,storiesViewModel.videoStatus.value)

            }
            composable(AllScreens.Saved.name) {


            }

            composable(route = "fullScreen",

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