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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devmike.storiessaver.model.STATUS_TYPE
import com.devmike.storiessaver.model.Status
import com.devmike.storiessaver.screens.AllScreens
import com.devmike.storiessaver.screens.StatusList

import com.devmike.storiessaver.screens.components.StoryTabRow
import com.devmike.storiessaver.viewmodel.StoriesViewModel
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
        StoriesSaverTheme {
            val navController = rememberNavController()
            val allscreens = AllScreens.values().toList()
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentScreen = AllScreens.fromRoute(backStackEntry.value?.destination?.route)
            Scaffold(
                topBar = {
                    StoryTabRow(
                        allScreens = allscreens,
                        onTabSelected = { screen -> navController.navigate(screen.name) },
                        currentScreen = currentScreen
                    )
                }, floatingActionButton = {
                    RefreshStatus(storiesViewModel)
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
                StatusList(navHostController = navHostController,storiesViewModel.imageStatus.value)


            }
            composable(AllScreens.Videos.name) {
                StatusList(navHostController = navHostController,storiesViewModel.videoStatus.value)

            }
            composable(AllScreens.Saved.name) {


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
    fun RefreshStatus(storiesViewModel: StoriesViewModel = viewModel()) {
        FloatingActionButton(
            onClick = {
                storiesViewModel.getFiles()

            },
            elevation = FloatingActionButtonDefaults.elevation(6.dp)
        ) {
            Icon(painterResource(id = R.drawable.ic_baseline_refresh_24), contentDescription = " Refresh Icon")


        }


    }
}