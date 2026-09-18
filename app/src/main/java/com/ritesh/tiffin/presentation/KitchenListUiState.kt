package com.ritesh.tiffin.presentation

import com.ritesh.tiffin.data.Kitchen

sealed interface KitchenListUiState {
    data object Loading : KitchenListUiState
    data class Success(val kitchens : List<Kitchen>) : KitchenListUiState
    data object Empty : KitchenListUiState
    data object Error : KitchenListUiState
}