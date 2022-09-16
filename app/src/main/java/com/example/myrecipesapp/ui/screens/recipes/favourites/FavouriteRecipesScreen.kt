package com.example.myrecipesapp.ui.screens.recipes.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myrecipesapp.ui.MainViewModel
import com.example.myrecipesapp.ui.screens.destinations.LoginScreenDestination
import com.example.myrecipesapp.ui.screens.destinations.RecipeDetailsScreenDestination
import com.example.myrecipesapp.ui.screens.destinations.RecipesSearchScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Destination
@Composable
fun FavouriteRecipesScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val state = viewModel.favouritesState

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = {
                        navigator.navigateUp()
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                        )
                    }
                }
            }
        },
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Brush.linearGradient(
                        listOf(
                            Color(0xFF3700B3),
                            Color(0xFFFF9800)
                        )
                    ))
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                Text(
                    text = "Search for a New Recipe",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { navigator.navigate(RecipesSearchScreenDestination) }
                )
                Text(
                    text = "Logout",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            navigator.popBackStack(LoginScreenDestination, true)
                            navigator.navigate(LoginScreenDestination)
                        }
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(bottom = 40.dp),
            contentPadding = PaddingValues(10.dp),
        ) {
            items(state.results.size) { i ->
                val item = state.results[i]
                Card(
                    onClick = { navigator.navigate(RecipeDetailsScreenDestination(id = item.id)) },
                    elevation = 5.dp,
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black
                                        ),
                                        startY = 300f
                                    )
                                )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = item.title,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            IconButton(onClick = {
                                viewModel.handleFavourite(item.id)
                            }) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(10.0.dp))
            }
        }
    }
}