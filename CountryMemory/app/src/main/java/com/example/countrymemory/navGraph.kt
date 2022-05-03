import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.countrymemory.Game
import com.example.countrymemory.Home
import com.example.countrymemory.Screen
import com.example.countrymemory.core.Country


@Composable
fun SetupGraph(
    navController: NavHostController,
    countries: List<Country>,
    fullCountriesNumber: Int
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            Home(navController = navController,fullCountriesNumber = fullCountriesNumber, countries = countries)
        }
        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("number") {
                type = NavType.IntType
            })
        ) {
            Game(navController = navController, countries =  countries, pickedNumber=it.arguments?.getInt("number") ?: 1)
        }
    }

}