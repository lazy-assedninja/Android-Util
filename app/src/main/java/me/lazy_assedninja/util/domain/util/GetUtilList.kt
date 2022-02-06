package me.lazy_assedninja.util.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.lazy_assedninja.util.data.Resource
import me.lazy_assedninja.util.data.repository.UtilRepository
import me.lazy_assedninja.util.di.IODispatcher
import me.lazy_assedninja.util.library.testing.OpenForTesting
import me.lazy_assedninja.util.ui.util.di.UtilScope
import javax.inject.Inject

@OpenForTesting
@UtilScope
class GetUtilList @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val utilRepository: UtilRepository
) {

    suspend operator fun invoke(): Resource<List<String>> = withContext(dispatcher) {
        return@withContext Resource.success(utilRepository.getUtilList())
    }
}