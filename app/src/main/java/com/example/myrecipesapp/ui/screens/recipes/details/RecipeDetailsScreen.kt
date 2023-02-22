package com.example.myrecipesapp.ui.screens.recipes.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myrecipesapp.BuildConfig
import com.example.myrecipesapp.ui.MainViewModel
import com.example.myrecipesapp.ui.screens.destinations.FavouriteRecipesScreenDestination
import com.example.myrecipesapp.ui.screens.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun RecipeDetailsScreen(
    id: Int,
    navigator: DestinationsNavigator,
    viewModel: MainViewModel
) {
    val state = viewModel.recipeInfoState

    val isFavExists = rememberSaveable { mutableStateOf(true) }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val columnWeight = .33f

    LaunchedEffect(key1 = true) {
        viewModel.getRecipeInfo(id.toString())
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = { navigator.navigateUp() }) {
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
                    .background(
                        Brush.linearGradient(
                        listOf(
                            Color(0xFF3700B3),
                            Color(0xFFFF9800)
                        )
                    ))
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                when {
                    BuildConfig.FLAVOR.contains("paid") ->
                        Text(
                            text = "My Favourite Recipes",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                    }
                                    navigator.navigate(FavouriteRecipesScreenDestination)
                                }
                        )
                }
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
        },
        floatingActionButton = {
            when {
                BuildConfig.FLAVOR.contains("paid") ->
                    FloatingActionButton(onClick = {
                        isFavExists.value = viewModel.handleFavourite(id)
                    }) {
                        isFavExists.value = viewModel.isFavourite(id)
                        if (isFavExists.value) Icon(
                            Icons.Filled.Star,
                            contentDescription = "Added to Favourites"
                        )
                        else Icon(
                            Icons.Filled.StarOutline,
                            contentDescription = "Add to Favourites"
                        )
                    }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(128.dp)
            )
            Text(
                text = state.title,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Serves: ${state.servings}",
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = "Ready In: ${state.readyInMinutes}mins",
                style = MaterialTheme.typography.h6,
            )
            Spacer(Modifier.height(10.0.dp))
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                contentPadding = PaddingValues(bottom = 8.dp)) {
                item {
                    Row {
                        TableCell(text = "Ingredient", weight = columnWeight)
                        TableCell(text = "Amount", weight = columnWeight)
                        TableCell(text = "Unit", weight = columnWeight)
                    }
                }
                items(state.extendedIngredients.size) { i ->
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = state.extendedIngredients[i].name, weight = columnWeight)
                        TableCell(text = state.extendedIngredients[i].measures.metric.amount.toString(), weight = columnWeight)
                        TableCell(text = state.extendedIngredients[i].measures.metric.unitShort, weight = columnWeight)
                    }
                }
                item {
                    state.instructions?.let { Text(text = it) }
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(8.dp),
        textAlign = TextAlign.Center
    )
}