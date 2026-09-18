package com.ritesh.tiffin.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KitchenListScreen(
    uiState: KitchenListUiState,
    onRetry: () -> Unit,
    onKitchenClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
    ) {
        Text(
            text = "Kitchens near you",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            when (uiState) {
                KitchenListUiState.Loading -> LoadingContent()

                is KitchenListUiState.Success -> KitchenListContent(
                    kitchens = uiState.kitchens,
                    onKitchenClick = onKitchenClick,
                )

                KitchenListUiState.Empty -> KitchenListMessage(
                    title = "No kitchens found",
                    message = "There are no kitchens available right now.",
                    buttonText = "Try again",
                    onButtonClick = onRetry,
                )

                KitchenListUiState.Error -> KitchenListMessage(
                    title = "Something went wrong",
                    message = "We couldn't load the kitchens.",
                    buttonText = "Retry",
                    onButtonClick = onRetry,
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
