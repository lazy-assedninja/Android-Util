package me.lazy_assedninja.sample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.lazy_assedninja.library_dagger.di.OpenForTesting
import me.lazy_assedninja.sample.vo.Utils

/**
 * Interface for database access for Utils related operations.
 */
@Dao
@OpenForTesting
interface UtilsDAO{

}