package com.example.finalproject.json

import kotlinx.serialization.Serializable

@Serializable
data class PojoKanjiAllJson(
    val character: String,
    val grade: Int? = null,
)