package me.lazy_assedninja.util.ui.encrypt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import me.lazy_assedninja.util.common.MainCoroutineRule
import me.lazy_assedninja.util.common.mock
import me.lazy_assedninja.util.data.Resource
import me.lazy_assedninja.util.domain.encrypt.DecryptBase642AES
import me.lazy_assedninja.util.domain.encrypt.DecryptBase642DES
import me.lazy_assedninja.util.domain.encrypt.EncryptAES2Base64
import me.lazy_assedninja.util.domain.encrypt.EncryptDES2Base64
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
class EncryptViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val data = "Lazy-assed Ninja"
    private val encryptedData = "xxx"
    private val key = "key"
    private val transformation = "transformation"
    private val iv = "iv"
    private val result = "result"
    private val encryptAES2Base64 = mock<EncryptAES2Base64>()
    private val encryptDES2Base64 = mock<EncryptDES2Base64>()
    private val decryptBase642AES = mock<DecryptBase642AES>()
    private val decryptBase642DES = mock<DecryptBase642DES>()

    private lateinit var viewModel: EncryptViewModel

    @Before
    fun init() {
        viewModel = EncryptViewModel(
            encryptAES2Base64, encryptDES2Base64, decryptBase642AES, decryptBase642DES
        )
    }

    @Test
    fun checkInputData() {
        viewModel.checkInputData(true)

        assertThat(getValue(viewModel.checkInputData).peekContent(), `is`(true))
    }

    @Test
    fun encryptAES() = runTest {
        `when`(encryptAES2Base64.invoke(data, key, transformation, iv))
            .thenReturn(Resource.success(result))
        viewModel.encrypt("AES", data, key, transformation, iv)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(encryptAES2Base64).invoke(data, key, transformation, iv)
            assertThat(getValue(viewModel.result).peekContent(), `is`(result))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun encryptAESError() = runTest {
        val message = "Error."
        `when`(encryptAES2Base64.invoke(data, key, transformation, iv))
            .thenReturn(Resource.error(message, null))
        viewModel.encrypt("AES", data, key, transformation, iv)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(encryptAES2Base64).invoke(data, key, transformation, iv)
            assertThat(getValue(viewModel.snackBarText).peekContent(), `is`(message))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun encryptDES() = runTest {
        `when`(encryptDES2Base64.invoke(data, key, transformation, iv))
            .thenReturn(Resource.success(result))
        viewModel.encrypt("DES", data, key, transformation, iv)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(encryptDES2Base64).invoke(data, key, transformation, iv)
            assertThat(getValue(viewModel.result).peekContent(), `is`(result))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun encryptDESError() = runTest {
        val message = "Error."
        `when`(encryptDES2Base64.invoke(data, key, transformation, iv))
            .thenReturn(Resource.error(message, null))
        viewModel.encrypt("DES", data, key, transformation, iv)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(encryptDES2Base64).invoke(data, key, transformation, iv)
            assertThat(getValue(viewModel.snackBarText).peekContent(), `is`(message))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun decryptAES() = runTest {
        `when`(decryptBase642AES.invoke(encryptedData, key, transformation, iv))
            .thenReturn(Resource.success(result))
        viewModel.decrypt("AES", encryptedData, key, transformation, iv)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(decryptBase642AES).invoke(encryptedData, key, transformation, iv)
            assertThat(getValue(viewModel.result).peekContent(), `is`(result))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun decryptAESError() = runTest {
        val message = "Error."
        `when`(decryptBase642AES.invoke(encryptedData, key, transformation, iv))
            .thenReturn(Resource.error(message, null))
        viewModel.decrypt("AES", encryptedData, key, transformation, iv)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(decryptBase642AES).invoke(encryptedData, key, transformation, iv)
            assertThat(getValue(viewModel.snackBarText).peekContent(), `is`(message))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun decryptDES() = runTest {
        `when`(decryptBase642DES.invoke(encryptedData, key, transformation, iv))
            .thenReturn(Resource.success(result))
        viewModel.decrypt("DES", encryptedData, key, transformation, iv)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(decryptBase642DES).invoke(encryptedData, key, transformation, iv)
            assertThat(getValue(viewModel.result).peekContent(), `is`(result))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }

    @Test
    fun decryptDESError() = runTest {
        val message = "Error."
        `when`(decryptBase642DES.invoke(encryptedData, key, transformation, iv))
            .thenReturn(Resource.error(message, null))
        viewModel.decrypt("DES", encryptedData, key, transformation, iv)

        assertThat(getValue(viewModel.dismissKeyboard).peekContent(), `is`(Unit))
        assertThat(getValue(viewModel.isLoading), `is`(true))

        withContext(mainCoroutineRule.dispatcher) {
            verify(decryptBase642DES).invoke(encryptedData, key, transformation, iv)
            assertThat(getValue(viewModel.snackBarText).peekContent(), `is`(message))
            assertThat(getValue(viewModel.isLoading), `is`(false))
        }
    }
}