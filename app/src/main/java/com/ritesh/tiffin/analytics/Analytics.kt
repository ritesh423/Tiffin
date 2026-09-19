package com.ritesh.tiffin.analytics


import android.util.Log

interface Analytics {
    fun log(event: String)
}

class LogcatAnalytics : Analytics {

    override fun log(event: String) {
        Log.d(LOG_TAG, event)
    }

    private companion object {
        const val LOG_TAG = "TiffinAnalytics"
    }
}

object AnalyticsEvents {
    const val KITCHEN_LIST_LOADED = "kitchen_list_loaded"
    const val KITCHEN_DETAIL_OPENED = "kitchen_detail_opened"
    const val PAYWALL_VIEWED = "paywall_viewed"
    const val PURCHASE_COMPLETED = "purchase_completed"
}