package com.example.finalproject.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalproject.roomdatabase.converter.DateConverters
import com.example.finalproject.roomdatabase.dao.SpacedDao
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.roomdatabase.roomentity.Story

@Database(entities = [SpacedEntity::class,Story::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class SpacedDatabase : RoomDatabase() {

    abstract fun spacedDao() : SpacedDao

}