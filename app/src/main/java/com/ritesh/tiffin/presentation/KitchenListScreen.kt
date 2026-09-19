package com.ritesh.tiffin.presentation

import androidx.compose.foundation.layout.Arrangement
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
import com.ritesh.tiffin.utils.KitchenListUiState

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
        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                top = 20.dp,
                end = 20.dp,
                bottom = 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "TIFFIN",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = "Kitchens near you",
                style = MaterialTheme.typography.headlineMedium,
            )

            Text(
                text = "Fresh, home-cooked meals made nearby.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()

        Text(
            text = "Finding kitchens near you…",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}
