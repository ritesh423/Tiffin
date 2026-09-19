package com.ritesh.tiffin.navigation


object Routes {
    const val KITCHEN_LIST = "kitchen_list"

    const val KITCHEN_ID_ARGUMENT = "kitchenId"

    const val KITCHEN_DETAIL =
        "kitchen_detail/{$KITCHEN_ID_ARGUMENT}"

    fun kitchenDetail(kitchenId: Int): String {
        return "kitchen_detail/$kitchenId"
    }
}