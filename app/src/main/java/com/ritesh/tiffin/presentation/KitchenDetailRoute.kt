package com.ritesh.tiffin.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ritesh.tiffin.utils.KitchenListUiState

@Composable
fun KitchenDetailRoute(
    kitchenId: Int,
    uiState: KitchenListUiState,
    onBack: () -> Unit,
    onSubscribe: () -> Unit,
) {
    when (uiState) {
        KitchenListUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is KitchenListUiState.Success -> {
            val kitchen = uiState.kitchens.firstOrNull { kitchen ->
                kitchen.id == kitchenId
            }

            if (kitchen == null) {
                DetailMessage(
                    title = "Kitchen not found",
                    message = "This kitchen is no longer available.",
                    onBack = onBack,
                )
            } else {
                KitchenDetailScreen(
                    kitchen = kitchen,
                    onBack = onBack,
                    onSubscribe = onSubscribe,
                )
            }
        }

        KitchenListUiState.Empty -> {
            DetailMessage(
                title = "Kitchen not found",
                message = "There are no kitchens available.",
                onBack = onBack,
            )
        }

        KitchenListUiState.Error -> {
            DetailMessage(
                title = "Unable to load kitchen",
                message = "Go back and retry loading the kitchens.",
                onBack = onBack,
            )
        }
    }
}

@Composable
private fun DetailMessage(
    title: String,
    message: String,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
        )

        TextButton(
            onClick = onBack,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Text("Back")
        }
    }
}