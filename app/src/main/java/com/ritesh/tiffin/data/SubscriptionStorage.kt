package com.ritesh.tiffin.data

import android.content.Context

class SubscriptionStorage(
    context: Context,
) {
    private val preferences = context.applicationContext
        .getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE,
        )

    fun recordLaunch(): Int {
        val updatedLaunchCount = getLaunchCount() + 1

        preferences.edit()
            .putInt(KEY_LAUNCH_COUNT, updatedLaunchCount)
            .commit()

        return updatedLaunchCount
    }

    fun getLaunchCount(): Int {
        return preferences.getInt(KEY_LAUNCH_COUNT, 0)
    }

    fun isPaid(): Boolean {
        return preferences.getBoolean(KEY_IS_PAID, false)
    }

    fun markAsPaid(): Boolean {
        return preferences.edit()
            .putBoolean(KEY_IS_PAID, true)
            .commit()
    }

    private companion object {
        const val PREFERENCES_NAME = "subscription_preferences"
        const val KEY_LAUNCH_COUNT = "launch_count"
        const val KEY_IS_PAID = "is_paid"
    }
}