package com.thepparat.heisigwithspacedrepetition.derialization

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Kanji2Element(context: Context) {
    private val jsonString = context.assets.open("kanji2element.json").bufferedReader().use {
        it.readText()
    }
    private val data = Json.decodeFromString<HashMap<String, List<String>>>(jsonString)
    fun getElement(kanji:String): List<String>? {
        return data[kanji]
    }
}