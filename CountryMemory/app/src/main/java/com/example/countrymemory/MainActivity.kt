package com.example.countrymemory


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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.countrymemory.core.Country
import com.example.countrymemory.core.JavaUtils
import com.example.countrymemory.ui.theme.CountryMemoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryMemoryTheme {
                var countries = JavaUtils.pickRandom(Country.loadCountries(context = LocalContext.current),6);
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Splash(countries)
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var countries = Country.loadCountries(context = LocalContext.current)
    println(countries)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryNamesGrid(countries: List<Country>) {

    LazyVerticalGrid(
        cells = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(countries.size) { i ->
            FlipCardCountryNames(countries[(0..countries.size).shuffled().first()].name)
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



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountryMemoryTheme {
        Greeting("Android")
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
                }.padding(4.dp).height(50.dp)
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
                }.padding(4.dp).height(50.dp)
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
