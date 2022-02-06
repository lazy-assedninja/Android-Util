package me.lazy_assedninja.util.data.repository

import android.content.Context
import me.lazy_assedninja.util.common.mock
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class UtilsRepositoryTest {

    private val context = mock<Context>()

    private lateinit var repository: UtilRepository

    @Before
    fun init() {
        repository = UtilRepository(context)
    }

    @Test
    fun getUtilList() {
        val util = "util"
        `when`(context.getString(anyInt())).thenReturn(util)

        val loaded = repository.getUtilList()
        assertThat(loaded.size, `is`(1))
        assertThat(loaded[0], `is`(util))
    }
}