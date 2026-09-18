package com.ritesh.tiffin

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

    KitchenListScreen(
        uiState = uiState,
        onRetry = kitchenViewModel::loadKitchens,
        onKitchenClick = { _ ->
            // Navigation will be added in Step 4.
        },
    )
}

