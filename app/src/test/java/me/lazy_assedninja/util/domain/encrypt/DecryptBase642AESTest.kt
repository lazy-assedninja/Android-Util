package me.lazy_assedninja.util.domain.encrypt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.lazy_assedninja.util.common.MainCoroutineRule
import me.lazy_assedninja.util.common.mock
import me.lazy_assedninja.util.data.Resource
import me.lazy_assedninja.util.data.Status
import me.lazy_assedninja.util.library.util.Encrypt
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DecryptBase642AESTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val encryptedData = "Lazy-assed Ninja"
    private val key = "key"
    private val transformation = "transformation"
    private val iv = "iv"
    private val result = "result"
    private val encrypt = mock<Encrypt>()

    private lateinit var decryptBase642AES: DecryptBase642AES

    @Before
    fun init() {
        decryptBase642AES = DecryptBase642AES(mainCoroutineRule.dispatcher, encrypt)
    }

    @Test
    fun invoke() = runTest {
        `when`(encrypt.decryptBase642AES(encryptedData, key, transformation, iv)).thenReturn(result)

        val loaded = decryptBase642AES.invoke(encryptedData, key, transformation, iv)
        verify(encrypt).decryptBase642AES(encryptedData, key, transformation, iv)
        assertThat(loaded, `is`(Resource.success(result)))
    }

    @Test
    fun exception() = runTest {
        val exception = Exception("Exception.")
        `when`(encrypt.decryptBase642AES(encryptedData, key, transformation, iv))
            .thenThrow(exception)

        val loaded = decryptBase642AES.invoke(encryptedData, key, transformation, iv)
        verify(encrypt).decryptBase642AES(encryptedData, key, transformation, iv)
        assertThat(loaded.status, `is`(Status.ERROR))
        assertThat(loaded.message, `is`(exception.toString()))
        assertThat(loaded.data, `is`(nullValue()))
    }
}