package com.example.myrecipesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myrecipesapp.ui.screens.NavGraphs
import com.example.myrecipesapp.ui.theme.MyRecipesAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyRecipesAppTheme {
                AppNavigation(this)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppNavigation(
    activity: MainActivity
) {
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        dependenciesContainerBuilder = {
            dependency(hiltViewModel<MainViewModel>(activity))
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    AzimRecipesAppTheme {
//        Greeting("Android")
//    }
//}