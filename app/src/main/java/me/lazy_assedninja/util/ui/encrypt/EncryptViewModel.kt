package me.lazy_assedninja.util.ui.encrypt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.lazy_assedninja.util.data.Event
import me.lazy_assedninja.util.data.Status
import me.lazy_assedninja.util.domain.encrypt.DecryptBase642AES
import me.lazy_assedninja.util.domain.encrypt.DecryptBase642DES
import me.lazy_assedninja.util.domain.encrypt.EncryptAES2Base64
import me.lazy_assedninja.util.domain.encrypt.EncryptDES2Base64
import me.lazy_assedninja.util.ui.encrypt.di.EncryptScope
import javax.inject.Inject

@EncryptScope
class EncryptViewModel @Inject constructor(
    private val encryptAES2Base64: EncryptAES2Base64,
    private val encryptDES2Base64: EncryptDES2Base64,
    private val decryptBase642AES: DecryptBase642AES,
    private val decryptBase642DES: DecryptBase642DES
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _result = MutableLiveData<Event<String>>()
    val result: LiveData<Event<String>>
        get() = _result

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>>
        get() = _snackBarText

    private val _checkInputData = MutableLiveData<Event<Boolean>>()
    val checkInputData: LiveData<Event<Boolean>>
        get() = _checkInputData

    private val _dismissKeyboard = MutableLiveData<Event<Unit>>()
    val dismissKeyboard: LiveData<Event<Unit>>
        get() = _dismissKeyboard

    fun checkInputData(isEncrypt: Boolean) {
        _checkInputData.value = Event(isEncrypt)
    }

    fun encrypt(type: String, data: String, key: String, transformation: String, iv: String?) {
        _dismissKeyboard.value = Event(Unit)
        _isLoading.value = true

        viewModelScope.launch {
            when (type) {
                "AES" -> {
                    val encryptResult = encryptAES2Base64(data, key, transformation, iv)

                    if (encryptResult.status == Status.SUCCESS) {
                        encryptResult.data?.let {
                            _result.value = Event(it)
                        }
                    } else {
                        encryptResult.message?.let {
                            _snackBarText.value = Event(it)
                        }
                    }
                }
                "DES" -> {
                    val encryptResult = encryptDES2Base64(data, key, transformation, iv)

                    if (encryptResult.status == Status.SUCCESS) {
                        encryptResult.data?.let {
                            _result.value = Event(it)
                        }
                    } else {
                        encryptResult.message?.let {
                            _snackBarText.value = Event(it)
                        }
                    }
                }
            }

            _isLoading.value = false
        }
    }

    fun decrypt(type: String, data: String, key: String, transformation: String, iv: String?) {
        _dismissKeyboard.value = Event(Unit)
        _isLoading.value = true

        viewModelScope.launch {
            when (type) {
                "AES" -> {
                    val decryptResult = decryptBase642AES(data, key, transformation, iv)

                    if (decryptResult.status == Status.SUCCESS) {
                        decryptResult.data?.let {
                            _result.value = Event(it)
                        }
                    } else {
                        decryptResult.message?.let {
                            _snackBarText.value = Event(it)
                        }
                    }
                }
                "DES" -> {
                    val decryptResult = decryptBase642DES(data, key, transformation, iv)

                    if (decryptResult.status == Status.SUCCESS) {
                        decryptResult.data?.let {
                            _result.value = Event(it)
                        }
                    } else {
                        decryptResult.message?.let {
                            _snackBarText.value = Event(it)
                        }
                    }
                }
            }

            _isLoading.value = false
        }
    }
}