package me.lazy_assedninja.library_dagger.utils

import java.util.concurrent.Executor

interface Executor {
    fun diskIO(): Executor

    fun networkIO(): Executor

    fun mainThread(): Executor
}