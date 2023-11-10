package com.qwertyuiop.localdatawhite.dto

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appTable")
@Keep
data class AppDto(
    @PrimaryKey
    val id: String
)