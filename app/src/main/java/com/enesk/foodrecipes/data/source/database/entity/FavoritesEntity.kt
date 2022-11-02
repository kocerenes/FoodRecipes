package com.enesk.foodrecipes.data.source.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enesk.foodrecipes.data.source.network.model.Result
import com.enesk.foodrecipes.util.Constants.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)