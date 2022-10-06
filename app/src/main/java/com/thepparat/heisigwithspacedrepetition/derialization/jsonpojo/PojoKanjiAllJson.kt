package com.thepparat.heisigwithspacedrepetition.derialization.jsonpojo

import kotlinx.serialization.Serializable

@Serializable
data class PojoKanjiAllJson(
    val character: String,
    val grade: Int? = null,
)