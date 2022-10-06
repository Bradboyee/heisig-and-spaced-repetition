package com.thepparat.heisigwithspacedrepetition.derialization.jsonpojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PojoRadicalItem(
    @SerialName("Examples")
    val examples: String,
    @SerialName("Hiragana-Romaji")
    val hiragana_romaji: String,
    @SerialName("Meaning")
    val meaning: String,
    @SerialName("Position")
    val position: String,
    @SerialName("Radical")
    val radical: String,
    @SerialName("Stroke count")
    val stroke_count: Int
)