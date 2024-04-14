package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo (
    @PrimaryKey val id: Int? = null,
    val title : String,
    val desc : String,
    var isChecked : Boolean,
    var timeStamp : Long,
)