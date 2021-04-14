package me.lazy_assedninja.library_dagger.utils

import android.content.Context
import me.lazy_assedninja.library_dagger.di.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class FileUtils @Inject constructor(context: Context) : File {

}