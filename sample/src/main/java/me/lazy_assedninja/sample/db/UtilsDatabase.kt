package me.lazy_assedninja.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.lazy_assedninja.sample.vo.Utils

/**
 * Main database description.
 */
@Database(
    entities = [Utils::class],
    version = 1,
    exportSchema = false
)
abstract class UtilsDatabase : RoomDatabase() {
    abstract fun utilsDAO(): UtilsDAO
}