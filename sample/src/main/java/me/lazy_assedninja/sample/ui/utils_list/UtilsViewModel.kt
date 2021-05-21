package me.lazy_assedninja.sample.ui.utils_list

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.library_dagger.testing.OpenForTesting
import me.lazy_assedninja.sample.repository.UtilsRepository
import me.lazy_assedninja.sample.vo.Utils
import javax.inject.Inject

@OpenForTesting
class UtilsViewModel @Inject constructor(
    private val utilsRepository: UtilsRepository
) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val utilsList = MutableLiveData<List<Utils>>()

    fun loadUtilsList() {
        isLoading.set(true)
        viewModelScope.launch {
            utilsList.postValue(utilsRepository.loadUtils())
        }
    }
}