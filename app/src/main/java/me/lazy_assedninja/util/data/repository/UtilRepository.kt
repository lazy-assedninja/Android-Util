package me.lazy_assedninja.util.data.repository

import android.content.Context
import me.lazy_assedninja.util.R
import me.lazy_assedninja.util.library.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Util types.
 */
@OpenForTesting
@Singleton
class UtilRepository @Inject constructor(private val context: Context) {

    fun getUtilList(): List<String> {
        return listOf(
            context.getString(R.string.navigation_encrypt_fragment)
        )
    }
}