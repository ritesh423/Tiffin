package com.ritesh.tiffin.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ritesh.tiffin.data.Kitchen

@Composable
fun KitchenListContent(
    kitchens: List<Kitchen>,
    onKitchenClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            start = 20.dp,
            top = 4.dp,
            end = 20.dp,
            bottom = 24.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        items(
            items = kitchens,
            key = { kitchen -> kitchen.id },
        ) { kitchen ->
            KitchenCard(
                kitchen = kitchen,
                onClick = { onKitchenClick(kitchen.id) },
            )
        }
    }
}
