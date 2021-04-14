package me.lazy_assedninja.library.utils

import android.util.Log
import me.lazy_assedninja.library.BuildConfig

@Suppress("unused")
class LogUtils {
    companion object{
        private val isDeBug: Boolean = BuildConfig.DEBUG

        fun v(tag: String?, msg: String) {
            if (!isDeBug) return
            Log.v(tag, msg)
        }

        fun d(tag: String?, msg: String) {
            if (!isDeBug) return
            Log.d(tag, msg)
        }

        fun i(tag: String?, msg: String) {
            if (!isDeBug) return
            Log.i(tag, msg)
        }

        fun w(tag: String?, msg: String) {
            if (!isDeBug) return
            Log.w(tag, msg)
        }

        fun e(tag: String?, msg: String) {
            if (!isDeBug) return
            Log.e(tag, msg)
        }
    }
}