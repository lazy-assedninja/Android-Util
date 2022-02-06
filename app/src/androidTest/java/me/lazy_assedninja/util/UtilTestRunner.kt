package me.lazy_assedninja.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import me.lazy_assedninja.util.application.TestApplication

/**
 * Custom runner to disable dependency injection.
 */
class UtilTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}