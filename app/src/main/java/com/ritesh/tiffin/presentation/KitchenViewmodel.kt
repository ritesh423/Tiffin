package com.ritesh.tiffin.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.tiffin.data.KitchenRepository
import com.ritesh.tiffin.utils.KitchenListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.ritesh.tiffin.analytics.Analytics
import com.ritesh.tiffin.analytics.AnalyticsEvents
import com.ritesh.tiffin.analytics.LogcatAnalytics

class KitchenViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val repository = KitchenRepository(application)
    private val analytics: Analytics = LogcatAnalytics()

    private val _uiState = MutableStateFlow<KitchenListUiState>(
        KitchenListUiState.Loading,
    )

    val uiState: StateFlow<KitchenListUiState> = _uiState.asStateFlow()

    init {
        loadKitchens()
    }

    fun loadKitchens() {
        _uiState.value = KitchenListUiState.Loading

        viewModelScope.launch {
            val result = repository.loadKitchens()

            _uiState.value = result.fold(
                onSuccess = { kitchens ->
                    analytics.log(
                        AnalyticsEvents.KITCHEN_LIST_LOADED,
                    )

                    if (kitchens.isEmpty()) {
                        KitchenListUiState.Empty
                    } else {
                        KitchenListUiState.Success(kitchens)
                    }
                },
                onFailure = {
                    KitchenListUiState.Error
                },
            )
        }
    }

    fun trackKitchenDetailOpened() {
        analytics.log(
            AnalyticsEvents.KITCHEN_DETAIL_OPENED,
        )
    }
}