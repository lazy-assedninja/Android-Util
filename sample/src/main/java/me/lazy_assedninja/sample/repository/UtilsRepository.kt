package me.lazy_assedninja.sample.repository

import android.content.Context
import me.lazy_assedninja.library_dagger.di.OpenForTesting
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.vo.Utils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Utils instances.
 */
@Singleton
@OpenForTesting
class UtilsRepository @Inject constructor(private val context: Context) {

    fun loadUtils() : List<Utils>{
        return listOf(
            Utils(context.getString(R.string.title_encrypt_utils_demo)),
        )
    }
}