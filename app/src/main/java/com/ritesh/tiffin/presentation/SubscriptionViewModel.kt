package com.ritesh.tiffin.presentation

import com.ritesh.tiffin.utils.SubscriptionUiState


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ritesh.tiffin.analytics.Analytics
import com.ritesh.tiffin.analytics.AnalyticsEvents
import com.ritesh.tiffin.analytics.LogcatAnalytics
import com.ritesh.tiffin.data.SubscriptionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SubscriptionViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val storage = SubscriptionStorage(application)
    private val analytics: Analytics = LogcatAnalytics()

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
        if (_uiState.value.shouldShowPaywall) {
            trackPaywallViewed()
        }
    }

    fun completePurchase(): Boolean {
        val wasSaved = storage.markAsPaid()

        if (wasSaved) {
            _uiState.value = _uiState.value.copy(
                isPaid = true,
            )

            analytics.log(
                AnalyticsEvents.PURCHASE_COMPLETED,
            )
        }

        return wasSaved
    }

    fun trackPaywallViewed() {
        analytics.log(
            AnalyticsEvents.PAYWALL_VIEWED,
        )
    }
}