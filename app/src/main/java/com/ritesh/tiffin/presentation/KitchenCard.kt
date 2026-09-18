package com.ritesh.tiffin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ritesh.tiffin.data.Kitchen

@Composable
fun KitchenCard(
    kitchen: Kitchen,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = kitchen.name,
                style = MaterialTheme.typography.titleLarge,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = kitchen.cuisine,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = if (kitchen.isVegetarian) {
                        "Vegetarian"
                    } else {
                        "Non-vegetarian"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "₹${kitchen.pricePerTiffin} per tiffin",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "★ ${kitchen.rating}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}