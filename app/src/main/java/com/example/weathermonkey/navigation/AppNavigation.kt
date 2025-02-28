package com.example.weathermonkey.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weathermonkey.WeatherViewModel
import com.example.weathermonkey.ui.screens.HistoryScreen
import com.example.weathermonkey.ui.screens.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(weatherViewModel: WeatherViewModel = koinViewModel()) {
    var selectedNavItems by remember { mutableStateOf(NavModel.First) }
    val navController: NavHostController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White.copy(alpha = 0.05f)
            ) {
                NavModel.entries.forEach { item ->
                    NavigationBarItem(
                        selected = selectedNavItems == item,
                        onClick = {
                            selectedNavItems = item
                            navController.popBackStack()
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                item.label,
                                modifier = Modifier.scale(1.4f),
                                tint = Color.White
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                color = Color.White
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            startDestination = HomeView,
            navController = navController,
        ) {

            composable<HomeView> {
                HomeScreen(
                    weatherViewModel = weatherViewModel,
                )
            }
            composable<HistoryView> {
                HistoryScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}