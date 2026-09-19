package com.ritesh.tiffin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.ritesh.tiffin.presentation.PaywallScreen
import com.ritesh.tiffin.presentation.SubscriptionViewModel
import com.ritesh.tiffin.ui.theme.TiffinTheme

class MainActivity : ComponentActivity() {

    private val subscriptionViewModel: SubscriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            subscriptionViewModel.recordLaunch()
        }

        enableEdgeToEdge()

        setContent {
            TiffinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    TiffinApp()
                }
            }
        }
    }
}


@Composable
fun TiffinApp(
    kitchenViewModel: KitchenViewModel = viewModel(),
    subscriptionViewModel: SubscriptionViewModel = viewModel(),
) {
    val kitchenUiState by
    kitchenViewModel.uiState.collectAsStateWithLifecycle()

    val subscriptionUiState by
    subscriptionViewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    val startDestination = remember {
        if (subscriptionUiState.shouldShowPaywall) {
            Routes.PAYWALL
        } else {
            Routes.KITCHEN_LIST
        }
    }

    fun leavePaywall() {
        val returnedToPreviousScreen =
            navController.popBackStack()

        if (!returnedToPreviousScreen) {
            navController.navigate(Routes.KITCHEN_LIST) {
                popUpTo(Routes.PAYWALL) {
                    inclusive = true
                }

                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Routes.KITCHEN_LIST) {
            KitchenListScreen(
                uiState = kitchenUiState,
                onRetry = kitchenViewModel::loadKitchens,
                onKitchenClick = { kitchenId ->
                    kitchenViewModel.trackKitchenDetailOpened()

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
                uiState = kitchenUiState,
                isPaid = subscriptionUiState.isPaid,
                onBack = {
                    navController.popBackStack()
                },
                onSubscribe = {
                    if (!subscriptionUiState.isPaid) {
                        subscriptionViewModel.trackPaywallViewed()
                        navController.navigate(Routes.PAYWALL)
                    }
                },
            )
        }

        composable(Routes.PAYWALL) {
            PaywallScreen(
                onPurchase = {
                    val purchaseSaved =
                        subscriptionViewModel.completePurchase()

                    if (purchaseSaved) {
                        leavePaywall()
                    }
                },
                onClose = {
                    leavePaywall()
                },
            )
        }
    }
}
