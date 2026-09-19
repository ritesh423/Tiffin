package com.ritesh.tiffin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Tiffin Plus",
            style = MaterialTheme.typography.headlineLarge,
        )

        Text(
            text = "Unlock weekly menus and delivery slots.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp),
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "₹1 is charged now.",
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = "Your ₹249/month plan starts on $planStartDate.",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "After that, you will be charged ₹249 each month.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        Button(
            onClick = onPurchase,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
        ) {
            Text("Start ₹1 trial")
        }

        TextButton(
            onClick = onClose,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Text("Not now")
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