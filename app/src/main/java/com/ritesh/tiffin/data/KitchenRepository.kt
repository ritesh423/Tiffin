package com.ritesh.tiffin.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class KitchenRepository(
    private val context: Context,
) {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun loadKitchens(): Result<List<Kitchen>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val jsonText = context.assets
                    .open("kitchens.json")
                    .bufferedReader()
                    .use { reader -> reader.readText() }

                json.decodeFromString<List<Kitchen>>(jsonText)
            }
        }
    }
}