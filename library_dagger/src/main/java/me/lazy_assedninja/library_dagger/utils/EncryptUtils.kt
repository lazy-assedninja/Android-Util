package me.lazy_assedninja.library_dagger.utils

import android.util.Base64
import me.lazy_assedninja.library_dagger.testing.OpenForTesting
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class EncryptUtils @Inject constructor(private val logUtils: LogUtils) : Encrypt {

    /**
     * Return the Base64-encode bytes of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation. (e.g. DES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @return the Base64-encode string of DES encryption
     */
    override fun encryptDES2Base64(
        data: String, key: String, transformation: String, iv: String?
    ): String {
        val content = encryptDES(
            data.toByteArray(Charset.forName("UTF-8")),
            key.toByteArray(Charset.forName("UTF-8")),
            transformation,
            iv?.toByteArray(Charset.forName("UTF-8"))
        )

        return content?.let {
            Base64.encodeToString(content, Base64.NO_WRAP)
        } ?: run {
            logUtils.e("EncryptUtils - encryptDES2Base64: ", "Content null.")
            data
        }
    }

    /**
     * Return the bytes of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation. (e.g. DES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @return the bytes of DES encryption
     */
    override fun encryptDES(
        data: ByteArray,
        key: ByteArray,
        transformation: String,
        iv: ByteArray?
    ): ByteArray? {
        return symmetricTemplate(data, key, "DES", transformation, iv, true)
    }

    /**
     * Return the string of DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation. (e.g. DES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @return the string of DES decryption
     */
    override fun decryptBase64DES(
        data: String, key: String, transformation: String, iv: String?
    ): String {
        val content = decryptAES(
            Base64.decode(data, Base64.NO_WRAP),
            key.toByteArray(),
            transformation,
            iv?.toByteArray()
        )

        return content?.let {
            String(content, Charset.forName("UTF-8"))
        } ?: run {
            logUtils.e("EncryptUtils - decryptBase64DES: ", "Content null.")
            data
        }
    }

    /**
     * Return the bytes of DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation. (e.g. DES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @return the bytes of DES decryption
     */
    override fun decryptDES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray? {
        return symmetricTemplate(data, key, "DES", transformation, iv, false)
    }

    /**
     * Return the Base64-encode bytes of AES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation. (e.g. AES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @return the Base64-encode string of AES encryption
     */
    override fun encryptAES2Base64(
        data: String, key: String, transformation: String, iv: String?
    ): String {
        val content = encryptAES(
            data.toByteArray(Charset.forName("UTF-8")),
            key.toByteArray(Charset.forName("UTF-8")),
            transformation,
            iv?.toByteArray(Charset.forName("UTF-8"))
        )

        return content?.let {
            Base64.encodeToString(content, Base64.NO_WRAP)
        } ?: run {
            logUtils.e("EncryptUtils - encryptAES2Base64: ", "Content null.")
            data
        }
    }

    /**
     * Return the bytes of AES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation. (e.g. AES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @return the bytes of AES encryption
     */
    override fun encryptAES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray? {
        return symmetricTemplate(data, key, "AES", transformation, iv, true)
    }

    /**
     * Return the string of AES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation. (e.g. AES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @return the string of AES decryption
     */
    override fun decryptBase64AES(
        data: String, key: String, transformation: String, iv: String?
    ): String {
        val content = decryptAES(
            Base64.decode(data, Base64.NO_WRAP),
            key.toByteArray(),
            transformation,
            iv?.toByteArray()
        )

        return content?.let {
            String(content, Charset.forName("UTF-8"))
        } ?: run {
            logUtils.e("EncryptUtils - decryptBase64AES: ", "Content null.")
            data
        }
    }

    /**
     * Return the bytes of AES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation. (e.g. AES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @return the bytes of AES decryption
     */
    override fun decryptAES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray? {
        return symmetricTemplate(data, key, "AES", transformation, iv, false)
    }

    /**
     * Encrypt or decrypt data.
     *
     * @param data           The data.
     * @param key            The key.
     * @param algorithm      The type of encrypt.
     * @param transformation The name of the transformation. (e.g. AES/CBC/PKCS5Padding)
     * @param iv             The buffer with the IV. The contents of the buffer are copied to
     *                       protect against subsequent modification.
     * @param isEncrypt      Choose to encrypt or decrypt data.
     * @return the bytes of AES decryption
     */
    private fun symmetricTemplate(
        data: ByteArray,
        key: ByteArray,
        algorithm: String,
        transformation: String,
        iv: ByteArray?,
        isEncrypt: Boolean
    ): ByteArray? {
        return try {
            val secretKey: SecretKey = if ("DES" == algorithm) {
                val keyFactory = SecretKeyFactory.getInstance(algorithm)
                val desKey = DESKeySpec(key)
                keyFactory.generateSecret(desKey)
            } else SecretKeySpec(key, algorithm)

            val cipher = Cipher.getInstance(transformation)
            iv?.let {
                cipher.init(
                    if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE,
                    secretKey,
                    IvParameterSpec(it)
                )
            } ?: run {
                cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey)
            }
            cipher.doFinal(data)
        } catch (e: Throwable) {
            logUtils.e("EncryptUtils - symmetricTemplate: ", e.toString())
            null
        }
    }
}