package me.lazy_assedninja.library_dagger.base

import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLog::class])
open class BaseTest {

    @Before
    fun setUp() {
        ShadowLog.stream = System.out
    }
}