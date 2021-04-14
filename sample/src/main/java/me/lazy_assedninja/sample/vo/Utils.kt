package me.lazy_assedninja.sample.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["name"])
data class Utils(
    @field:SerializedName("name")
    val name: String
)