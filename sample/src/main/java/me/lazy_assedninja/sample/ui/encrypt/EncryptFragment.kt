package me.lazy_assedninja.sample.ui.encrypt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.EditorInfo.IME_ACTION_NEXT
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import me.lazy_assedninja.library_dagger.ui.BaseFragment
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentEncryptBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import javax.inject.Inject

class EncryptFragment : BaseFragment() {

    var binding by autoCleared<FragmentEncryptBinding>()

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EncryptViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Obtaining the login graph from MainActivity and instantiate
        // the @Inject fields with objects from the graph
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_encrypt,
            container,
            false,
            dataBindingComponent
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            binding.spinnerEncryptType.adapter = ArrayAdapter.createFromResource(
                it,
                R.array.array_encrypt_type, android.R.layout.simple_list_item_1
            )
            binding.spinnerEncryptPattern.adapter = ArrayAdapter.createFromResource(
                it,
                R.array.array_encrypt_pattern, android.R.layout.simple_list_item_1
            )
            binding.spinnerFilling.adapter = ArrayAdapter.createFromResource(
                it,
                R.array.array_filling, android.R.layout.simple_list_item_1
            )
            binding.spinnerEncryptPattern.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    view?.let { v -> dismissKeyboard(v.windowToken) }
                    if (binding.spinnerEncryptPattern.selectedItem.toString() == "ECB") {
                        binding.tilIv.visibility = View.GONE
                        binding.tilKey.editText?.imeOptions = IME_ACTION_DONE
                    } else {
                        binding.tilIv.visibility = View.VISIBLE
                        binding.tilKey.editText?.imeOptions = IME_ACTION_NEXT
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }

        binding.btEncrypt.setOnClickListener {
            handleData(it, true)
        }
        binding.btDecrypt.setOnClickListener {
            handleData(it, false)
        }

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            binding.tilData.editText?.setText(result)
            viewModel.isLoading.set(false)
        })

        initNavigationUI()
    }

    private fun initNavigationUI() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }

    private fun handleData(view: View, isEncrypt: Boolean) {
        dismissKeyboard(view.windowToken)
        viewModel.isLoading.set(true)

        val type = binding.spinnerEncryptType.selectedItem.toString()
        var data = ""
        var key = ""
        val transformation = binding.spinnerEncryptType.selectedItem.toString() + "/" +
                binding.spinnerEncryptPattern.selectedItem.toString() + "/" +
                binding.spinnerFilling.selectedItem.toString()
        var iv: String? = null
        var infoValidation = true
        context?.let { context ->
            binding.tilData.editText?.let { it ->
                if (it.text.isEmpty()) {
                    binding.tilData.error = context.getString(R.string.error_data_can_not_be_empty)
                    infoValidation = false
                } else {
                    binding.tilData.error = null
                    data = it.text.toString()
                }
            }
            binding.tilKey.editText?.let { it ->
                var keyLength = 0
                var errorToastID = 0
                when (binding.spinnerEncryptType.selectedItem.toString()) {
                    "AES" -> {
                        keyLength = 16
                        errorToastID = R.string.error_key_length_need_to_be_sixteen
                    }
                    "DES" -> {
                        keyLength = 8
                        errorToastID = R.string.error_key_length_need_to_be_eight
                    }
                }
                if (it.text.length != keyLength) {
                    binding.tilKey.error =
                        context.getString(errorToastID)
                    infoValidation = false
                } else {
                    binding.tilKey.error = null
                    key = it.text.toString()
                }
            }
            if (binding.tilIv.visibility != View.GONE) {
                var ivLength = 0
                var errorToastID = 0
                when (binding.spinnerEncryptType.selectedItem.toString()) {
                    "AES" -> {
                        ivLength = 16
                        errorToastID = R.string.error_iv_length_need_to_be_sixteen
                    }
                    "DES" -> {
                        ivLength = 8
                        errorToastID = R.string.error_iv_length_need_to_be_eight
                    }
                }
                binding.tilIv.editText?.let { it ->
                    if (it.text.length != ivLength) {
                        binding.tilIv.error =
                            context.getString(errorToastID)
                        infoValidation = false
                    } else {
                        binding.tilIv.error = null
                        iv = it.text.toString()
                    }
                }
            }
        }
        if (!infoValidation) {
            viewModel.isLoading.set(false)
            return
        }

        // Return result
        when (isEncrypt) {
            true -> viewModel.encrypt(type, data, key, transformation, iv)
            false -> viewModel.decrypt(type, data, key, transformation, iv)
        }
    }
}