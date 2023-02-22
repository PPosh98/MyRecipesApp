package com.example.myrecipesapp.ui.screens.recipes.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myrecipesapp.BuildConfig
import com.example.myrecipesapp.model.recipes.ResultModel
import com.example.myrecipesapp.ui.MainViewModel
import com.example.myrecipesapp.ui.screens.destinations.FavouriteRecipesScreenDestination
import com.example.myrecipesapp.ui.screens.destinations.LoginScreenDestination
import com.example.myrecipesapp.ui.screens.destinations.RecipeDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun RecipesSearchScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel
) {
    val state = viewModel.screenState

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            RecipesSearchBar(
                text = viewModel.searchTextState.value,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onSearchClicked = {
                    viewModel.getRecipes(it)
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            ) {
                Row(horizontalArrangement = Arrangement.End) {
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
                        )
                    )
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
        }
    ) { padding ->
        RecipesList(
            state = state,
            onLoadNextItems = { viewModel.loadNextItems() },
            onNavigate = { id -> navigator.navigate(RecipeDetailsScreenDestination(id)) }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecipesSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { onTextChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    keyboardController?.hide()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@Composable
fun RecipesList(
    state: ScreenState,
    onLoadNextItems: () -> Unit,
    onNavigate: (Int) -> Unit
){
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(state.items.size) { i ->
            val item = state.items[i]
            LaunchedEffect(scrollState) {
                if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    onLoadNextItems()
                }
            }
            RecipesListItem(
                item = item,
                onNavigate = onNavigate
            )
        }
        item {
            if (state.isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipesListItem(
    onNavigate: (Int) -> Unit,
    item: ResultModel
){
    Card(
        onClick = { onNavigate(item.id) },
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
            Box(modifier = Modifier
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

        }
    }
    Spacer(Modifier.height(10.0.dp))
}