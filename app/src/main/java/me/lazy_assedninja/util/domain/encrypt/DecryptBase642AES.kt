package me.lazy_assedninja.util.domain.encrypt

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.lazy_assedninja.util.data.Resource
import me.lazy_assedninja.util.di.IODispatcher
import me.lazy_assedninja.util.library.testing.OpenForTesting
import me.lazy_assedninja.util.library.util.Encrypt
import me.lazy_assedninja.util.ui.encrypt.di.EncryptScope
import javax.inject.Inject

@OpenForTesting
@EncryptScope
class DecryptBase642AES @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val encrypt: Encrypt
) {

    suspend operator fun invoke(
        data: String,
        key: String,
        transformation: String,
        iv: String?
    ): Resource<String> = withContext(dispatcher) {
        try {
            return@withContext Resource.success(
                encrypt.decryptBase642AES(
                    data,
                    key,
                    transformation,
                    iv
                )
            )
        } catch (e: Exception) {
            return@withContext Resource.error(e.toString(), null)
        }
    }
}