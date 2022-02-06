package me.lazy_assedninja.util.di

import dagger.Module
import dagger.Provides
import me.lazy_assedninja.util.library.util.AppExecutors
import me.lazy_assedninja.util.library.util.Encrypt
import me.lazy_assedninja.util.library.util.EncryptUtil
import javax.inject.Singleton

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
class LibraryModule {

    @Singleton
    @Provides
    fun provideAppExecutor(): AppExecutors {
        return AppExecutors()
    }

    @Singleton
    @Provides
    fun provideEncryptUtil(): Encrypt {
        return EncryptUtil()
    }
}