package me.lazy_assedninja.util.library

import me.lazy_assedninja.util.library.util.EncryptUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EncryptUtilsTest {

    private val data = "Lazy-assed Ninja"
    private val resultAES = "Szp0NRXpgM9B65kit2Ggrf//CRlQPNJoRFAPnaaUyhU="
    private val resultDES = "K0deFvsFU1uRy0qHJBxv+LT1H1SndaGd"
    private val keyAES = "abcdabcdabcdabcd" // Need 16, 24, 32 bytes
    private val keyDES = "abcdabcd" // Need 8 bytes
    private val transformationAES = "AES/CBC/pkcs5Padding"
    private val transformationDES = "DES/CBC/pkcs5Padding"
    private val ivAES = "0102030405060708" // Need 16, 24, 32 bytes
    private val ivDES = "12345678" // Need 8 bytes

    private lateinit var encryptUtil: EncryptUtil

    @Before
    fun init() {
        encryptUtil = EncryptUtil()
    }

    @Test
    fun encryptAES2Base64() {
        val result = encryptUtil.encryptAES2Base64(data, keyAES, transformationAES, ivAES)
        assertThat(result, `is`(resultAES))
    }

    @Test
    fun decryptBase64AES() {
        val result = encryptUtil.decryptBase642AES(resultAES, keyAES, transformationAES, ivAES)
        assertThat(result, `is`(data))
    }

    @Test
    fun encryptDES2Base64() {
        val result = encryptUtil.encryptDES2Base64(data, keyDES, transformationDES, ivDES)
        assertThat(result, `is`(resultDES))
    }

    @Test
    fun decryptBase64DES() {
        val result = encryptUtil.decryptBase642DES(resultDES, keyDES, transformationDES, ivDES)
        assertThat(result, `is`(data))
    }
}