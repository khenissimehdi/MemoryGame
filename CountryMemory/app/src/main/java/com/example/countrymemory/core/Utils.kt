package com.example.countrymemory.core


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.Composable
import org.json.JSONArray
import org.json.JSONObject

data class Country(val code: String, val name: String) {
    private var cachedFlag: Bitmap? = null

    fun getFlag(context: Context): Bitmap? {
        return cachedFlag ?: run {
            cachedFlag = loadFlag(context, code)
            Log.d("bitmap", "$cachedFlag")
            cachedFlag
        }
    }

    companion object {
        private const val COUNTRIES_FILE = "countries.json"
        private const val FLAGS_DIRECTORY = "flags"

        private fun loadFlag(context: Context, code: String): Bitmap {
            return context.assets.open("$FLAGS_DIRECTORY/${code.lowercase()}.png").use {
                BitmapFactory.decodeStream(it)
            }
        }

        fun loadCountries(context: Context): List<Country> {
            val json = JSONArray(context.assets.open(COUNTRIES_FILE).bufferedReader().use { it.readText() })
            val result = mutableListOf<Country>()
            (0 until json.length()).forEach {
                val obj = json.getJSONObject(it)
                result.add(Country(obj.getString("code"), obj.getString("name")))
            }
            return result
        }
    }
}