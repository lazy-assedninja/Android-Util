package me.lazy_assedninja.library_dagger.utils

import me.lazy_assedninja.library_dagger.base.BaseTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EncryptUtilsTest : BaseTest() {

    private val encryptUtils = EncryptUtils(LogUtils())

    private val data = "lazy-assedninja"
    private val resultAES = "0bkMkDp5AJydMiyy7Thh7g=="
    private val resultDES = "AyUFDly0EAP114iChB3zbA=="
    private val keyAES = "abcdabcdabcdabcd" // Need 16 bytes
    private val keyDES = "abcdabcd" // Need 8 bytes
    private val transformationAES = "AES/CBC/pkcs5Padding"
    private val transformationDES = "DES/CBC/pkcs5Padding"
    private val ivAES = "0102030405060708" // Need 16 bytes
    private val ivDES = "12345678" // Need 8 bytes

    @Test
    fun encryptAES2Base64() {
        val result = encryptUtils.encryptAES2Base64(data, keyAES, transformationAES, ivAES)
        assertThat(result, `is`(resultAES))
    }

    @Test
    fun decryptBase64AES() {
        val result = encryptUtils.decryptBase64AES(resultAES, keyAES, transformationAES, ivAES)
        assertThat(result, `is`(data))
    }

    @Test
    fun encryptDES2Base64() {
        val result = encryptUtils.encryptDES2Base64(data, keyDES, transformationDES, ivDES)
        assertThat(result, `is`(resultDES))
    }

    @Test
    fun decryptBase64DES() {
        val result = encryptUtils.decryptBase64DES(resultDES, keyDES, transformationDES, ivDES)
        assertThat(result, `is`(data))
    }
}