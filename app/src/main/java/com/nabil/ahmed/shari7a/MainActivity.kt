package com.nabil.ahmed.shari7a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import com.nabil.ahmed.shari7a.navigation.Destination
import com.nabil.ahmed.shari7a.ui.screens.forecast.ForecastScreen
import com.nabil.ahmed.shari7a.ui.screens.input.InputScreen
import com.nabil.ahmed.shari7a.ui.screens.tariff.TariffScreen
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.nabil.ahmed.shari7a.ui.viewmodel.MainViewModel
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Shari7aTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    
    val backStack = rememberNavBackStack(Destination.Forecast)
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val currentDestination = backStack.last() as Destination
            AppBottomBar(
                currentDestination = currentDestination,
                onNavigate = { destination ->
                    if (backStack.last() != destination) {
                        backStack.clear()
                        backStack.add(destination)
                    }
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
            entryProvider = { key ->
                val navKey = key as NavKey
                when (navKey) {
                    Destination.Forecast -> NavEntry(navKey) { ForecastScreen(viewModel) }
                    Destination.Input -> NavEntry(navKey) { InputScreen(viewModel) }
                    Destination.Tariff -> NavEntry(navKey) {
                        TariffScreen(viewModel)
                    }
                    else -> NavEntry(navKey) { Text("Unknown") }
                }
            }
        )
    }
}

@Composable
fun AppBottomBar(
    currentDestination: Destination,
    onNavigate: (Destination) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination == Destination.Forecast,
            onClick = { onNavigate(Destination.Forecast) },
            icon = { Icon(Icons.Rounded.Speed, contentDescription = null) },
            label = { Text(stringResource(R.string.nav_forecast)) }
        )
        NavigationBarItem(
            selected = currentDestination == Destination.Input,
            onClick = { onNavigate(Destination.Input) },
            icon = { Icon(Icons.Rounded.EditNote, contentDescription = null) },
            label = { Text(stringResource(R.string.nav_input)) }
        )
        NavigationBarItem(
            selected = currentDestination == Destination.Tariff,
            onClick = { onNavigate(Destination.Tariff) },
            icon = { Icon(Icons.Rounded.GridView, contentDescription = null) },
            label = { Text(stringResource(R.string.nav_tariff)) }
        )
    }
}
