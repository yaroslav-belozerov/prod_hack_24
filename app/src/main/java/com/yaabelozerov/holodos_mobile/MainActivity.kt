package com.yaabelozerov.holodos_mobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yaabelozerov.holodos_mobile.domain.MainScreenViewModel
import com.yaabelozerov.holodos_mobile.domain.SettingsScreenViewModel
import com.yaabelozerov.holodos_mobile.mock.MockApi
import com.yaabelozerov.holodos_mobile.ui.MainPage
import com.yaabelozerov.holodos_mobile.ui.Navigation
import com.yaabelozerov.holodos_mobile.ui.SettingsPage
import com.yaabelozerov.holodos_mobile.ui.AddWidget
import com.yaabelozerov.holodos_mobile.ui.AuthPage
import com.yaabelozerov.holodos_mobile.ui.theme.Holodos_mobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainScreenViewModel by viewModels()
    private val settingsViewModel: SettingsScreenViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            var addWidgetOpen by remember { mutableStateOf(false) }
            var firstTime: Boolean = true

            Holodos_mobileTheme {
                if (!firstTime) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
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
                        },
                        topBar = {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary
                                ),
                                title = {
                                    val navigation = Navigation.entries.find {
                                        it.route == navController.currentBackStackEntryAsState().value?.destination?.route
                                    }
                                    if (navigation != null) {
                                        Text(
                                            text = stringResource(navigation.title)
                                        )
                                    }
                                }
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    addWidgetOpen = true
                                }
                            ) {
                                Icon(Icons.Filled.Add, "Add Product")
                            }
                            if (addWidgetOpen) AddWidget {
                                addWidgetOpen = false
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = Navigation.FRIDGE.route
                        ) {
                            composable(Navigation.SETTINGS.route) {
                                val user = settingsViewModel.users.collectAsState().value
                                SettingsPage(settingsViewModel, user)
                            }
                            composable(Navigation.FRIDGE.route) {
                                Column {
                                    val items = mainViewModel.items.collectAsState().value
                                    MainPage(items.map { Triple(it.name, it.daysUntilExpiry, it.quantity) })
                                }
                            }
                            composable(Navigation.LIST.route) {
                                Text("Shopping List")
                            }
                        }
                    }
                } else {
                    Scaffold { innerPadding ->
                        Column {
                            AuthPage(modifier = Modifier.padding(innerPadding)) {
                                settingsViewModel.login(
                                    it
                                )
                            }
                            val code = settingsViewModel.code.collectAsState().value
                            Text(code.toString())
                        }
                    }
                }


            }
        }
    }
}