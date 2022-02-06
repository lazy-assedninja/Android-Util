package me.lazy_assedninja.util.ui.encrypt

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.lazy_assedninja.util.R
import me.lazy_assedninja.util.data.EventObserver
import me.lazy_assedninja.util.databinding.EncryptFragmentBinding
import me.lazy_assedninja.util.library.ui.BaseFragment
import me.lazy_assedninja.util.ui.index.MainActivity
import me.lazy_assedninja.util.util.autoCleared
import javax.inject.Inject

class EncryptFragment : BaseFragment() {

    var binding by autoCleared<EncryptFragmentBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: EncryptViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Creates an instance of EncryptComponent by grabbing the factory from the MainComponent
        // and injects this fragment
        (activity as MainActivity).mainComponent.encryptComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EncryptFragmentBinding.inflate(inflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    private fun initView() {
        context?.let {
            binding.spinnerEncryptType.adapter = ArrayAdapter.createFromResource(
                it, R.array.array_encrypt_type, android.R.layout.simple_list_item_1
            )
            binding.spinnerEncryptPattern.adapter = ArrayAdapter.createFromResource(
                it, R.array.array_encrypt_pattern, android.R.layout.simple_list_item_1
            )
            binding.spinnerFilling.adapter = ArrayAdapter.createFromResource(
                it, R.array.array_filling, android.R.layout.simple_list_item_1
            )
            binding.spinnerEncryptPattern.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    if (position == 0) {
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
    }

    private fun initData() {
        viewModel.checkInputData.observe(viewLifecycleOwner, EventObserver {
            checkInputData(it)
        })
        viewModel.result.observe(viewLifecycleOwner, EventObserver {
            binding.tilData.editText?.setText(it)
        })
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { message ->
            view?.let { view ->
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
            }
        })
        viewModel.dismissKeyboard.observe(viewLifecycleOwner, EventObserver {
            dismissKeyboard(view?.windowToken)
        })
    }

    private fun checkInputData(isEncrypt: Boolean) {
        val type = binding.spinnerEncryptType.selectedItem.toString()
        val data = binding.tilData.editText?.text?.toString().let {
            if (it.isNullOrEmpty()) {
                binding.tilData.error = getString(R.string.error_data_can_not_be_empty)
                return@checkInputData
            } else {
                binding.tilData.error = null
                it
            }
        }
        val key = binding.tilKey.editText?.text?.toString().let {
            if (it.isNullOrEmpty()) {
                binding.tilKey.error = getString(R.string.error_key_can_not_be_empty)
                return@checkInputData
            } else {
                binding.tilKey.error = null
                it
            }
        }
        val transformation = binding.spinnerEncryptType.selectedItem.toString() + "/" +
                binding.spinnerEncryptPattern.selectedItem.toString() + "/" +
                binding.spinnerFilling.selectedItem.toString()
        val iv = binding.tilIv.editText?.text?.toString().let {
            binding.tilIv.error = null
            it
        }

        if (isEncrypt) {
            viewModel.encrypt(type, data, key, transformation, iv)
        } else {
            viewModel.decrypt(type, data, key, transformation, iv)
        }
    }
}