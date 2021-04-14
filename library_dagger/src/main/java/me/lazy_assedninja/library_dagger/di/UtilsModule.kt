package me.lazy_assedninja.library_dagger.di

import dagger.Binds
import dagger.Module
import me.lazy_assedninja.library_dagger.utils.Encrypt
import me.lazy_assedninja.library_dagger.utils.Executor
import me.lazy_assedninja.library_dagger.utils.File
import me.lazy_assedninja.library_dagger.utils.Log
import me.lazy_assedninja.library_dagger.utils.EncryptUtils
import me.lazy_assedninja.library_dagger.utils.ExecutorUtils
import me.lazy_assedninja.library_dagger.utils.FileUtils
import me.lazy_assedninja.library_dagger.utils.LogUtils
import javax.inject.Singleton

@Suppress("unused")
@Module
abstract class UtilsModule {

    @Singleton
    @Binds
    abstract fun bindExecutorUtils(executorUtils: ExecutorUtils) : Executor

    @Singleton
    @Binds
    abstract fun bindFileUtils(fileUtils: FileUtils) : File

    @Singleton
    @Binds
    abstract fun bindEncryptUtils(encryptUtils: EncryptUtils) : Encrypt

    @Singleton
    @Binds
    abstract fun bindLogUtils(logUtils: LogUtils) : Log
}