package com.ritesh.tiffin.utils

data class SubscriptionUiState(
    val launchCount: Int,
    val isPaid: Boolean,
) {
    val shouldShowPaywall: Boolean
        get() = launchCount >= 3 && !isPaid
}