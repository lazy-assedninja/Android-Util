package me.lazy_assedninja.util.domain.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.util.common.MainCoroutineRule
import me.lazy_assedninja.util.common.mock
import me.lazy_assedninja.util.data.Resource
import me.lazy_assedninja.util.data.repository.UtilRepository
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetUtilListTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val utilRepository = mock<UtilRepository>()

    private lateinit var getUtilList: GetUtilList

    @Before
    fun init() {
        getUtilList = GetUtilList(mainCoroutineRule.dispatcher, utilRepository)
    }

    @Test
    fun invoke() = runTest {
        val list = listOf("util")
        `when`(utilRepository.getUtilList()).thenReturn(list)

        val loaded = getUtilList.invoke()
        verify(utilRepository).getUtilList()
        assertThat(loaded, `is`(Resource.success(list)))
    }
}