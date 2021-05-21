package me.lazy_assedninja.sample.ui.utils_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.sample.repository.UtilsRepository
import me.lazy_assedninja.sample.utils.MainCoroutineScopeRule
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class UtilsViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineScope = MainCoroutineScopeRule()

    private val repository = mock(UtilsRepository::class.java)
    private var utilsViewModel = UtilsViewModel(repository)

    @Test
    fun testNull() {
        assertThat(utilsViewModel.utilsList, notNullValue())
    }

    @Test
    fun getUtilsList(){
        utilsViewModel.loadUtilsList()
        verify(repository).loadUtils()
    }
}