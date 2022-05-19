package com.vku.learnzone.navigation

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vku.learnzone.ui.theme.colorPrimary
import com.vku.learnzone.ui.theme.gray
import com.vku.learnzone.ui.theme.white
import com.vku.learnzone.view.*
import com.vku.learnzone.viewmodel.CourseViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = white, elevation = 16.dp) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Items.items.forEach {
                    BottomNavigationItem(
                        icon = {
                            it.icon?.let { it1 ->
                                Icon(
                                    imageVector = it1,
                                    contentDescription = "",
                                    tint = if (currentRoute == it.route) colorPrimary else gray
                                )
                            }
                        },
                        selected = currentRoute == it.route,
                        onClick = {
                            if (currentRoute != it.route) {
                                navController.graph.startDestinationRoute?.let { item ->
                                    navController.popBackStack(
                                        item,
                                        false
                                    )
                                }
                            }
                            if (currentRoute != it.route) {
                                navController.navigate(it.route) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {
        ScreenController(navController = navController)
    }
}

@Composable
fun ScreenController(navController: NavHostController) {
    val vm = CourseViewModel()
    NavHost(navController = navController, startDestination = Screen.ProfileScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
        composable(Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(vm)
        }
        composable(Screen.CoursesScreen.route) {
            CoursesScreen()
        }
        composable(Screen.WishlistScreen.route) {
            WishlistScreen()
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen()
        }
        composable(Screen.DetailsScreen.route) {
            DetailsScreen(navController = navController)
        }
        composable(Screen.PopularListScreen.route) {
            PopularListScreen(navController = navController)
        }
    }
}
