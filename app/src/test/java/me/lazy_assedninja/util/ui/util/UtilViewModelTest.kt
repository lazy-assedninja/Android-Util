package me.lazy_assedninja.util.ui.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import me.lazy_assedninja.util.common.MainCoroutineRule
import me.lazy_assedninja.util.common.mock
import me.lazy_assedninja.util.data.Resource
import me.lazy_assedninja.util.domain.util.GetUtilList
import me.lazy_assedninja.util.util.LiveDataUtil.getValue
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
class UtilViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val util = "util"
    private val utils = listOf(util)
    private val getUtilList = mock<GetUtilList>()

    private lateinit var viewModel: UtilViewModel

    @Before
    fun init() {
        runTest {
            `when`(getUtilList.invoke()).thenReturn(Resource.success(utils))
        }
        viewModel = UtilViewModel(getUtilList)
    }

    @Test
    fun loadUtilsWhenViewModelInit() = runTest {
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(getUtilList).invoke()
            assertThat(getValue(viewModel.utils), `is`(utils))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun openDemo() {
        viewModel.openUtil(util)

        assertThat(getValue(viewModel.openUtil).peekContent(), `is`(util))
    }
}