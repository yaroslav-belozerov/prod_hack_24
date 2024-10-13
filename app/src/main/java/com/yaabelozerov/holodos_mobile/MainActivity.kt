package com.yaabelozerov.holodos_mobile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yaabelozerov.holodos_mobile.domain.CartScreenViewModel
import com.yaabelozerov.holodos_mobile.domain.MainScreenViewModel
import com.yaabelozerov.holodos_mobile.domain.SettingsScreenViewModel
import com.yaabelozerov.holodos_mobile.domain.Sorting
import com.yaabelozerov.holodos_mobile.ui.AddQrWidget
import com.yaabelozerov.holodos_mobile.ui.MainPage
import com.yaabelozerov.holodos_mobile.ui.Navigation
import com.yaabelozerov.holodos_mobile.ui.SettingsPage
import com.yaabelozerov.holodos_mobile.ui.AddWidget
import com.yaabelozerov.holodos_mobile.ui.AuthPage
import com.yaabelozerov.holodos_mobile.ui.QrPage
import com.yaabelozerov.holodos_mobile.ui.ShoppingListPage
import com.yaabelozerov.holodos_mobile.ui.theme.Holodos_mobileTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainScreenViewModel by viewModels()
    private val settingsViewModel: SettingsScreenViewModel by viewModels()
    private val cartScreenViewModel: CartScreenViewModel by viewModels()
    var shouldShowCamera: MutableStateFlow<Boolean> = MutableStateFlow(false)

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var hasCameraPermission: Boolean = false
        val cameraPermissionRequestLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission granted: proceed with opening the camera
                    hasCameraPermission = true
                    shouldShowCamera.update { true }
                } else {
                    // Permission denied: inform the user to enable it through settings
                    Toast.makeText(
                        this,
                        "Go to settings and enable camera permission to use this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        setContent {
            val navController = rememberNavController()
            var addWidgetOpen by remember { mutableStateOf(false) }
            var sortModal by remember { mutableStateOf(false) }
            var addQRWidgetOpen by remember { mutableStateOf(false) }
            Holodos_mobileTheme {
                val l = settingsViewModel.loggedIn.collectAsState().value
                if (l != null) {
                    if (l) {
                        Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                            NavigationBar {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                Navigation.entries.filter { it.showInNavBar }.forEach { screen ->
                                    val selected =
                                        currentDestination?.hierarchy?.any { it.route == screen.route } == true
                                    NavigationBarItem(selected = selected, onClick = {
                                        navController.popBackStack(
                                            screen.route, inclusive = true, saveState = true
                                        )
                                        navController.navigate(screen.route)
                                    }, icon = {
                                        if (screen.filled != null && screen.outlined != null) Icon(
                                            imageVector = if (selected) screen.filled else screen.outlined,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = if (!selected) Modifier.alpha(0.4f) else Modifier
                                        )
                                        if (screen.resFilled != null && screen.resOutlined != null) Icon(
                                            painter = if (selected) painterResource(screen.resFilled) else painterResource(screen.resOutlined),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = (if (!selected) Modifier.alpha(0.4f) else Modifier).then(Modifier.size(32.dp))
                                        )
                                    })
                                }
                            }
                        }, topBar = {
                            CenterAlignedTopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            ), title = {
                                val navigation = Navigation.entries.find {
                                    it.route == navController.currentBackStackEntryAsState().value?.destination?.route
                                }
                                if (navigation != null) {
                                    Text(
                                        text = stringResource(navigation.title)
                                    )
                                }
                            })
                        }, floatingActionButton = {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            when (currentDestination?.route) {
                                Navigation.FRIDGE.route -> {
                                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        FloatingActionButton(onClick = {
                                            addWidgetOpen = true
                                        }) {
                                            Icon(Icons.Filled.Add, "Add Product")
                                        }
                                        val hol = settingsViewModel.hol.collectAsState().value.also { Log.i("hol", it.toString())}
                                        val curr = settingsViewModel.current.collectAsState().value.also { Log.i("curr", it.toString()) }
                                        if (addWidgetOpen && (hol != null && curr != null)) AddWidget(onSave = {
                                            mainViewModel.createItem(it, settingsViewModel.hol.value!!.id!!)
                                        }, holodos = hol, user = curr) {
                                            addWidgetOpen = false
                                        }
                                        FloatingActionButton(onClick = {
                                            sortModal = true
                                        }) {
                                            Icon(Icons.Filled.MoreVert, "Sort")
                                        }
                                        FloatingActionButton(onClick = {
                                            cameraPermissionRequestLauncher.launch(android.Manifest.permission.CAMERA)
                                            navController.navigate(Navigation.SCAN.route)
                                        }) {
                                            Icon(painterResource(R.drawable.qr_code), "Add Product by QR")
                                        }
                                    }
                                }
                                else -> {}
                            }
                        }) { innerPadding ->
                            NavHost(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                startDestination = Navigation.FRIDGE.route
                            ) {
                                composable(Navigation.SETTINGS.route) {
                                    val user = settingsViewModel.users.collectAsState().value
                                    SettingsPage(settingsViewModel, user, onAddUser = { phone, isSponsor ->
                                        settingsViewModel.addUser(phone, isSponsor)
                                    })
                                }
                                composable(Navigation.FRIDGE.route) {
                                    val items = mainViewModel.items.collectAsState().value
                                    settingsViewModel.hol.collectAsState().value?.let {
                                        MainPage(mainViewModel, it.id!!, items)
                                    }

                                    if (sortModal) ModalBottomSheet(onDismissRequest = { sortModal = false }) {
                                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Text(fontSize = 24.sp, text = stringResource(R.string.sort))
                                            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                                Sorting.entries.map {
                                                    if (it == mainViewModel.sort.collectAsState().value) {
                                                        Button(onClick = { mainViewModel.setSorting(Sorting.NONE) }) {
                                                            Text(stringResource(it.res))
                                                        }
                                                    } else {
                                                        OutlinedButton(onClick = { mainViewModel.setSorting(it) }) {
                                                            Text(stringResource(it.res))
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                composable(Navigation.LIST.route) {
                                    val items = cartScreenViewModel.items.collectAsState().value
                                    ShoppingListPage(items)
                                }
                                composable(Navigation.AUTH.route) {
                                    AuthPage(modifier = Modifier.padding(innerPadding)) {
                                        settingsViewModel.login(
                                            it
                                        )
                                    }
                                }
                                composable(Navigation.SCAN.route) {
                                    QrPage(shouldShowCamera.collectAsState().value) {

                                        AddQrWidget(items = mainViewModel.getQrData(it), onSave = {},
                                            onDismissRequest = {addQRWidgetOpen = true})
                                    }
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
}