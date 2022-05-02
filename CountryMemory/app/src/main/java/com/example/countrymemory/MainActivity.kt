package com.example.countrymemory


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countrymemory.core.Country
import com.example.countrymemory.ui.theme.CountryMemoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryMemoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Splash()
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
fun CountryNamesGrid() {
    var countries = Country.loadCountries(context = LocalContext.current)
    var countriesNames = countries.map { e -> e.name };

    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(countriesNames.size) { i ->
            Text(text = countriesNames[i], fontSize = 20.sp)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryFlagsGrid() {
    var countries = Country.loadCountries(context = LocalContext.current)
    var countriesCodes = countries.map { e -> e.code };

    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(20.dp)

    ) {
        items(countries.size) { i ->
            Card( modifier = Modifier.padding(4.dp),
                backgroundColor = Color.LightGray) {
                countries[i].getFlag(LocalContext.current)
                    ?.let { Image(bitmap = it.asImageBitmap(), contentDescription = "image") }
            }
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
fun Splash() {

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
                CountryFlagsGrid()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ){
            Column {
                CountryNamesGrid()
            }
        }
    }
}