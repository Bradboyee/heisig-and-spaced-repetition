package com.example.finalproject.retrofit.pojo

data class Radical(
    val animation: List<String>,
    val character: String,
    val image: String,
    val meaning: Meaning,
    val name: Name,
    val position: Position,
    val strokes: Int
)