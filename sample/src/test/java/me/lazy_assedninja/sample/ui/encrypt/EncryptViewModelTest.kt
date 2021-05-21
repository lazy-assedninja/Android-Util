package me.lazy_assedninja.sample.ui.encrypt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.lazy_assedninja.library_dagger.utils.EncryptUtils
import me.lazy_assedninja.sample.utils.MainCoroutineScopeRule
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class EncryptViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineScope = MainCoroutineScopeRule()

    private val encryptUtils = mock(EncryptUtils::class.java)
    private var encryptViewModel = EncryptViewModel(encryptUtils)

    @Test
    fun testNull() {
        assertThat(encryptViewModel.isLoading, notNullValue())
        assertThat(encryptViewModel.result, notNullValue())
    }

    @Test
    fun encryptAES() {
        encryptViewModel.encrypt("AES", anyString(), anyString(), anyString(), anyString())
        verify(encryptUtils).encryptAES2Base64(anyString(), anyString(), anyString(), anyString())
    }

    @Test
    fun encryptDES() {
        encryptViewModel.encrypt("DES", anyString(), anyString(), anyString(), anyString())
        verify(encryptUtils).encryptDES2Base64(anyString(), anyString(), anyString(), anyString())
    }

    @Test
    fun decryptAES() {
        encryptViewModel.decrypt("AES", anyString(), anyString(), anyString(), anyString())
        verify(encryptUtils).decryptBase64AES(anyString(), anyString(), anyString(), anyString())
    }

    @Test
    fun decryptDES() {
        encryptViewModel.decrypt("DES", anyString(), anyString(), anyString(), anyString())
        verify(encryptUtils).decryptBase64DES(anyString(), anyString(), anyString(), anyString())
    }
}