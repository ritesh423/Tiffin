package com.ritesh.tiffin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ritesh.tiffin.data.Kitchen

@Composable
fun KitchenDetailScreen(
    kitchen: Kitchen,
    onBack: () -> Unit,
    isPaid: Boolean,
    onSubscribe: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = onBack) {
                Text("Back")
            }

            Text(
                text = kitchen.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp),
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                KitchenSummary(kitchen = kitchen)
            }

            item {
                Text(
                    text = "Weekly menu",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }

            items(
                items = kitchen.weeklyMenu,
                key = { menu -> menu.day },
            ) { menu ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = menu.day,
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Text(
                            text = menu.dish,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }

        Button(
            onClick = onSubscribe,
            enabled = !isPaid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = if (isPaid) {
                    "Subscribed"
                } else {
                    "Subscribe"
                },
            )
        }
    }
}

@Composable
private fun KitchenSummary(
    kitchen: Kitchen,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = kitchen.cuisine,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = "₹${kitchen.pricePerTiffin} per tiffin",
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = if (kitchen.isVegetarian) {
                    "Vegetarian"
                } else {
                    "Non-vegetarian"
                },
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = "★ ${kitchen.rating}",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}