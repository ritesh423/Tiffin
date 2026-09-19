package com.ritesh.tiffin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun PaywallScreen(
    onPurchase: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val planStartDate = remember {
        formatPlanStartDate(
            timestamp = System.currentTimeMillis() +
                    TimeUnit.HOURS.toMillis(24),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        TextButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.End),
        ) {
            Text("Not now")
        }

        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(top = 28.dp),
        ) {
            Text(
                text = "TIFFIN PLUS",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 7.dp,
                ),
            )
        }

        Text(
            text = "Know what's cooking all week",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(top = 16.dp),
        )

        Text(
            text = "See weekly menus in advance and choose delivery slots that work for you.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 10.dp),
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "• Full weekly menus",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "• Preferred delivery slots",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "₹1 today",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

                Text(
                    text = "₹1 will be charged now.",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "₹249 will be charged on $planStartDate, then every month.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Button(
            onClick = onPurchase,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 20.dp)
                .heightIn(min = 54.dp),
        ) {
            Text("Start ₹1 trial")
        }
    }
}

private fun formatPlanStartDate(
    timestamp: Long,
): String {
    val formatter = SimpleDateFormat(
        "d MMMM yyyy 'at' h:mm a",
        Locale.getDefault(),
    )

    return formatter.format(Date(timestamp))
}
