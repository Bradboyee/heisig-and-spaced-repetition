package com.example.finalproject.json.jsonpojo

import kotlinx.serialization.Serializable

@Serializable
data class PojoKanjiAllJson(
    val character: String,
    val grade: Int? = null,
)