package me.lazy_assedninja.library.utils

import android.util.Base64
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Suppress("unused", "MemberVisibilityCanBePrivate", "SpellCheckingInspection")
class EncryptUtils {
    companion object{

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
        fun encryptDES2Base64(data: String, key: String, transformation: String, iv: String?): String {
            val content = encryptDES(
                data.toByteArray(Charset.forName("UTF-8")),
                key.toByteArray(Charset.forName("UTF-8")),
                transformation,
                iv?.toByteArray(Charset.forName("UTF-8"))
            )

            return if (content != null) {
                Base64.encodeToString(content, Base64.NO_WRAP)
            } else {
                LogUtils.e("EncryptUtils: ", "EncryptDES2Base64 Error.")
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
        fun encryptDES(
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
        fun decryptBase64DES(data: String, key: String, transformation: String, iv: String?): String {
            val content = decryptAES(
                Base64.decode(data, Base64.NO_WRAP),
                key.toByteArray(),
                transformation,
                iv?.toByteArray()
            )

            return if (content != null) {
                String(content, Charset.forName("UTF-8"))
            } else {
                LogUtils.e("EncryptUtils: ", "DecryptBase642DES Error.")
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
        fun decryptDES(
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
        fun encryptAES2Base64(data: String, key: String, transformation: String, iv: String?): String {
            val content = encryptAES(
                data.toByteArray(Charset.forName("UTF-8")),
                key.toByteArray(Charset.forName("UTF-8")),
                transformation,
                iv?.toByteArray(Charset.forName("UTF-8"))
            )

            return if (content != null) {
                Base64.encodeToString(content, Base64.NO_WRAP)
            } else {
                LogUtils.e("EncryptUtils: ", "EncryptAES2Base64 Error.")
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
        fun encryptAES(
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
        fun decryptBase64AES(data: String, key: String, transformation: String, iv: String?): String {
            val content = decryptAES(
                Base64.decode(data, Base64.NO_WRAP),
                key.toByteArray(),
                transformation,
                iv?.toByteArray()
            )

            return if (content != null) {
                String(content, Charset.forName("UTF-8"))
            } else {
                LogUtils.e("EncryptUtils: ", "DecryptBase642AES Error.")
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
        fun decryptAES(
            data: ByteArray, key: ByteArray, transformation: String, iv: ByteArray?
        ): ByteArray? {
            return symmetricTemplate(data, key, "AES", transformation, iv, false)
        }

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
                if (iv == null || iv.isEmpty()) {
                    cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey)
                } else {
                    cipher.init(
                        if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey,
                        IvParameterSpec(iv)
                    )
                }

                cipher.doFinal(data)
            } catch (e: Throwable) {
                LogUtils.e("EncryptUtils: ", e.toString())
                null
            }
        }
    }
}