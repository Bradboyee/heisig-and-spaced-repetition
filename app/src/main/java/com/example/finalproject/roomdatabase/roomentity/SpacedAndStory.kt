package com.example.finalproject.roomdatabase.roomentity

import androidx.room.Embedded
import androidx.room.Relation

data class SpacedAndStory(
    @Embedded val spacedEntity: SpacedEntity,
    @Relation(
        parentColumn = "kanji",
        entityColumn = "kanji"
    )
    val story: List<Story>,
) {
}