package me.lazy_assedninja.util.library.util

import android.util.Base64
import me.lazy_assedninja.util.library.testing.OpenForTesting
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Suppress("unused")
@OpenForTesting
class EncryptUtil : Encrypt {

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
    @Throws(Exception::class)
    override fun encryptDES2Base64(
        data: String,
        key: String,
        transformation: String,
        iv: String?
    ): String {
        return Base64.encodeToString(
            encryptDES(
                data.toByteArray(Charset.forName("UTF-8")),
                key.toByteArray(Charset.forName("UTF-8")),
                transformation,
                iv?.toByteArray(Charset.forName("UTF-8"))
            ),
            Base64.NO_WRAP
        )
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
    @Throws(Exception::class)
    override fun encryptDES(
        data: ByteArray,
        key: ByteArray,
        transformation: String,
        iv: ByteArray?
    ): ByteArray {
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
    @Throws(Exception::class)
    override fun decryptBase642DES(
        data: String,
        key: String,
        transformation: String,
        iv: String?
    ): String {
        return String(
            decryptDES(
                Base64.decode(data, Base64.NO_WRAP),
                key.toByteArray(),
                transformation,
                iv?.toByteArray()
            ),
            Charset.forName("UTF-8")
        )
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
    @Throws(Exception::class)
    override fun decryptDES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray {
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
    @Throws(Exception::class)
    override fun encryptAES2Base64(
        data: String,
        key: String,
        transformation: String,
        iv: String?
    ): String {
        return Base64.encodeToString(
            encryptAES(
                data.toByteArray(Charset.forName("UTF-8")),
                key.toByteArray(Charset.forName("UTF-8")),
                transformation,
                iv?.toByteArray(Charset.forName("UTF-8"))
            ),
            Base64.NO_WRAP
        )
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
    @Throws(Exception::class)
    override fun encryptAES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray {
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
    @Throws(Exception::class)
    override fun decryptBase642AES(
        data: String,
        key: String,
        transformation: String,
        iv: String?
    ): String {
        return String(
            decryptAES(
                Base64.decode(data, Base64.NO_WRAP),
                key.toByteArray(),
                transformation,
                iv?.toByteArray()
            ),
            Charset.forName("UTF-8")
        )
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
    @Throws(Exception::class)
    override fun decryptAES(
        data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
    ): ByteArray {
        return symmetricTemplate(data, key, "AES", transformation, iv, false)
    }

    @Throws(Exception::class)
    private fun symmetricTemplate(
        data: ByteArray,
        key: ByteArray,
        algorithm: String,
        transformation: String,
        iv: ByteArray?,
        isEncrypt: Boolean
    ): ByteArray {
        val secretKey = if ("DES" == algorithm) {
            val keyFactory = SecretKeyFactory.getInstance(algorithm)
            val desKey = DESKeySpec(key)
            keyFactory.generateSecret(desKey)
        } else SecretKeySpec(key, algorithm)

        val cipher = Cipher.getInstance(transformation)
        if (iv == null || iv.isEmpty()) {
            cipher.init(
                if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey
            )
        } else {
            cipher.init(
                if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey,
                IvParameterSpec(iv)
            )
        }
        return cipher.doFinal(data)
    }
}