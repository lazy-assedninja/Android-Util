package me.lazy_assedninja.sample.ui.encrypt

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.library_dagger.utils.EncryptUtils
import javax.inject.Inject

class EncryptViewModel @Inject constructor(private val encryptUtils: EncryptUtils) : ViewModel() {

    val isLoading = ObservableBoolean(false)
    val result: MediatorLiveData<String> = MediatorLiveData()

    fun encrypt(type: String, data: String, key: String, transformation: String, iv: String?) {
        viewModelScope.launch {
            when (type) {
                "AES" -> result.value =
                    encryptUtils.encryptAES2Base64(data, key, transformation, iv)
                "DES" -> result.value =
                    encryptUtils.encryptDES2Base64(data, key, transformation, iv)
            }
            isLoading.set(false)
        }
    }

    fun decrypt(type: String, data: String, key: String, transformation: String, iv: String?) {
        viewModelScope.launch {
            when (type) {
                "AES" -> result.value =
                    encryptUtils.decryptBase64AES(data, key, transformation, iv)
                "DES" -> result.value =
                    encryptUtils.decryptBase64DES(data, key, transformation, iv)
            }
            isLoading.set(false)
        }
    }
}