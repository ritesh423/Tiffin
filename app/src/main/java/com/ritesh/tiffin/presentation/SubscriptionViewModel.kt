package com.ritesh.tiffin.presentation

import com.ritesh.tiffin.utils.SubscriptionUiState


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ritesh.tiffin.data.SubscriptionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SubscriptionViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val storage = SubscriptionStorage(application)

    private val _uiState = MutableStateFlow(
        SubscriptionUiState(
            launchCount = storage.getLaunchCount(),
            isPaid = storage.isPaid(),
        ),
    )

    val uiState: StateFlow<SubscriptionUiState> =
        _uiState.asStateFlow()

    fun recordLaunch() {
        val updatedLaunchCount = storage.recordLaunch()

        _uiState.value = _uiState.value.copy(
            launchCount = updatedLaunchCount,
        )
    }

    fun completePurchase(): Boolean {
        val wasSaved = storage.markAsPaid()

        if (wasSaved) {
            _uiState.value = _uiState.value.copy(
                isPaid = true,
            )
        }

        return wasSaved
    }
}