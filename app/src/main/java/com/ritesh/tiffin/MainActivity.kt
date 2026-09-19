package com.ritesh.tiffin

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ritesh.tiffin.navigation.Routes
import com.ritesh.tiffin.presentation.KitchenDetailRoute
import com.ritesh.tiffin.presentation.KitchenListScreen
import com.ritesh.tiffin.presentation.KitchenViewModel
import com.ritesh.tiffin.ui.theme.TiffinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TiffinTheme {
                TiffinApp()
            }
        }
    }
}


@Composable
fun TiffinApp(
    kitchenViewModel: KitchenViewModel = viewModel(),
) {
    val uiState by kitchenViewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.KITCHEN_LIST,
    ) {
        composable(Routes.KITCHEN_LIST) {
            KitchenListScreen(
                uiState = uiState,
                onRetry = kitchenViewModel::loadKitchens,
                onKitchenClick = { kitchenId ->
                    navController.navigate(
                        Routes.kitchenDetail(kitchenId),
                    )
                },
            )
        }

        composable(
            route = Routes.KITCHEN_DETAIL,
            arguments = listOf(
                navArgument(Routes.KITCHEN_ID_ARGUMENT) {
                    type = NavType.IntType
                },
            ),
        ) { backStackEntry ->
            val kitchenId = backStackEntry.arguments
                ?.getInt(Routes.KITCHEN_ID_ARGUMENT)
                ?: -1

            KitchenDetailRoute(
                kitchenId = kitchenId,
                uiState = uiState,
                onBack = {
                    navController.popBackStack()
                },
                onSubscribe = {
                    // Paywall navigation will be added later.
                },
            )
        }
    }
}

