package com.example.movieapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recent")
data class Recent(
    @PrimaryKey
    val name: String)