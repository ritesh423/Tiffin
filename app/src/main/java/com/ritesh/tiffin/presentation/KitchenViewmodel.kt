package com.ritesh.tiffin.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.tiffin.data.KitchenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KitchenViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val repository = KitchenRepository(application)

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
}