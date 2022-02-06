package me.lazy_assedninja.util.library.util

import kotlin.jvm.Throws

interface Encrypt {

    @Throws(Exception::class)
    fun encryptDES2Base64(data: String, key: String, transformation: String, iv: String?): String

    @Throws(Exception::class)
    fun encryptDES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray

    @Throws(Exception::class)
    fun decryptBase642DES(data: String, key: String, transformation: String, iv: String?): String

    @Throws(Exception::class)
    fun decryptDES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray

    @Throws(Exception::class)
    fun encryptAES2Base64(data: String, key: String, transformation: String, iv: String?): String

    @Throws(Exception::class)
    fun encryptAES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray

    @Throws(Exception::class)
    fun decryptBase642AES(data: String, key: String, transformation: String, iv: String?): String

    @Throws(Exception::class)
    fun decryptAES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray
}