package me.lazy_assedninja.util.ui.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.util.data.Event
import me.lazy_assedninja.util.data.Status
import me.lazy_assedninja.util.domain.util.GetUtilList
import me.lazy_assedninja.util.ui.util.di.UtilScope
import javax.inject.Inject

@UtilScope
class UtilViewModel @Inject constructor(
    private val getUtilList: GetUtilList
) : ViewModel() {

    private val _openUtil = MutableLiveData<Event<String>>()
    val openUtil: LiveData<Event<String>>
        get() = _openUtil

    private val _utils = MutableLiveData<List<String>>().apply { value = emptyList() }
    val utils: LiveData<List<String>>
        get() = _utils

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        loadUtils()
    }

    private fun loadUtils() {
        _isLoading.value = true

        viewModelScope.launch {
            val utilResult = getUtilList()

            if (utilResult.status == Status.SUCCESS) {
                _utils.value = utilResult.data
            }
            _isLoading.value = false
        }
    }

    fun openUtil(util: String) {
        _openUtil.value = Event(util)
    }
}