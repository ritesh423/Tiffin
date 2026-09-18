package com.ritesh.tiffin.data
import kotlinx.serialization.Serializable

@Serializable
data class DailyMenu(
    val day: String,
    val dish: String,
)

@Serializable
data class Kitchen(
    val id: Int,
    val name: String,
    val cuisine: String,
    val pricePerTiffin: Int,
    val isVegetarian: Boolean,
    val rating: Double,
    val weeklyMenu: List<DailyMenu>,
)