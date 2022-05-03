package com.example.countrymemory


import SetupGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.countrymemory.core.Country
import com.example.countrymemory.core.JavaUtils
import com.example.countrymemory.ui.theme.CountryMemoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var navController: NavHostController
        super.onCreate(savedInstanceState)
        setContent {
            CountryMemoryTheme {
                var fullCountries = Country.loadCountries(context = LocalContext.current)
                navController = rememberNavController()
                SetupGraph(navController = navController,countries = fullCountries, fullCountriesNumber = fullCountries.size)
            }
        }
    }
}

@Composable
fun Game(navController: NavHostController, countries: List<Country>, pickedNumber: Int) {
    var RandomCountries = JavaUtils.pickRandom(countries,pickedNumber);
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Splash(RandomCountries)
    }
}

@Composable
fun Home(navController: NavHostController, fullCountriesNumber: Int, countries: List<Country>) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

        var sliderPosition by remember { mutableStateOf(1f) }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            Row() {
                Text(text = "Muscle Your Memory", fontSize = 40.sp ,fontFamily = FontFamily.Cursive,fontWeight = FontWeight.Bold)
            }
            Row() {
                Text(text = sliderPosition.toInt().toString(), fontSize = 30.sp)
            }
            Row() {
                Slider(value = sliderPosition,enabled = true,steps = fullCountriesNumber/3,valueRange = 1f..fullCountriesNumber.toFloat() ,onValueChange = { sliderPosition = it })
            }

            Row() {
                Button(
                    onClick = { navController.navigate(route = "game/${sliderPosition.toInt()}") },
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.DarkGray),
                    ){
                    Text(text = "Play",color = White, fontSize = 50.sp)
                }
            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryNamesGrid(countries: List<Country>) {

    LazyVerticalGrid(
        cells = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(countries.size) { i ->
            FlipCardCountryNames(countries[i].name)
        }
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryFlagsGrid(countries: List<Country>) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(8.dp)

    ) {
        items(countries.size) { i ->
            countries[i].getFlag(LocalContext.current)
                ?.let { FlipCardCountryFlags( it.asImageBitmap()) }
        }
    }
}



@Composable
fun Splash(countries: List<Country>) {

    Row(modifier =Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column() {
                CountryFlagsGrid(countries.shuffled())
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.TopCenter
        ){
            Column {
                CountryNamesGrid(countries.shuffled())
            }
        }
    }
}


@Composable
fun FlipCardCountryNames(name : String) {

    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateColor by animateColorAsState(
        targetValue = if (rotated) Color.Gray else Color.Gray,
        animationSpec = tween(500)
    )

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .padding(4.dp)
                .height(50.dp)
                .clickable {
                    rotated = !rotated
                },
            backgroundColor = animateColor
        )
        {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(text = if (rotated) name else  "Flip me",
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = if (rotated) animateBack else animateFront
                            rotationY = rotation
                        })
            }

        }
    }
}

@Composable
fun FlipCardCountryFlags(flag : ImageBitmap) {

    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateColor by animateColorAsState(
        targetValue = if (rotated) Color.Gray else Color.Gray,
        animationSpec = tween(500)
    )

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .padding(4.dp)
                .height(50.dp)
                .clickable {
                    rotated = !rotated
                },
            backgroundColor = animateColor
        )
        {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if(rotated) {
                    Image(bitmap = flag,modifier = Modifier.graphicsLayer {
                        alpha = animateBack
                        rotationY = rotation

                    } ,contentDescription = "image")
                } else {
                    Text(text = "Flip me",
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = animateFront
                                rotationY = rotation
                            })
                }

            }

        }
    }
}
