package me.lazy_assedninja.library_dagger.utils

interface Encrypt {
    fun encryptDES2Base64(data: String, key: String, transformation: String, iv: String?): String

    fun encryptDES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray?

    fun decryptBase64DES(data: String, key: String, transformation: String, iv: String?): String

    fun decryptDES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray?

    fun encryptAES2Base64(data: String, key: String, transformation: String, iv: String?): String

    fun encryptAES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray?

    fun decryptBase64AES(data: String, key: String, transformation: String, iv: String?): String

    fun decryptAES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray?
}