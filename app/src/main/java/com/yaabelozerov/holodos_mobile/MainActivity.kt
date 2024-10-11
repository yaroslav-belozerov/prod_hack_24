package com.yaabelozerov.holodos_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yaabelozerov.holodos_mobile.ui.Navigation
import com.yaabelozerov.holodos_mobile.ui.theme.Holodos_mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            Holodos_mobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        Navigation.entries.forEach { screen ->
                            val selected =
                                currentDestination?.hierarchy?.any { it.route == screen.route } == true
                            NavigationBarItem(selected = selected, onClick = {
                                navController.popBackStack(
                                    screen.route, inclusive = true, saveState = true
                                )
                                navController.navigate(screen.route)
                            }, icon = {
                                Icon(
                                    imageVector = if (selected) screen.filled else screen.outlined,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = if (!selected) Modifier.alpha(0.4f) else Modifier
                                )
                            })
                        }
                    }
                }) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Navigation.FRIDGE.route
                    ) {
                        composable(Navigation.SETTINGS.route) {
                            Text("Settings")
                        }
                        composable(Navigation.FRIDGE.route) {
                            Text("Fridge")
                        }
                        composable(Navigation.LIST.route) {
                            Text("Shopping List")
                        }
                    }
                }
            }
        }
    }
}