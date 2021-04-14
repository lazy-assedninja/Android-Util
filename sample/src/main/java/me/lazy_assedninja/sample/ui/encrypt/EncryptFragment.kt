package me.lazy_assedninja.sample.ui.encrypt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import me.lazy_assedninja.library_dagger.ui.BaseFragment
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentEncryptBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import me.lazy_assedninja.sample.view_model.ViewModelFactory
import javax.inject.Inject

class EncryptFragment : BaseFragment() {

    var binding by autoCleared<FragmentEncryptBinding>()

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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
                    binding.tilIv.visibility =
                        if (binding.spinnerEncryptPattern.selectedItem.toString() == "ECB") View.GONE
                        else View.VISIBLE
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }

        binding.btnEncrypt.setOnClickListener {
            viewModel.isLoading.set(true)
            handleData(it, true)
        }
        binding.btnDecrypt.setOnClickListener {
            viewModel.isLoading.set(true)
            handleData(it, false)
        }

        // Handle the back button click event
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(
                EncryptFragmentDirections.backToMainFragment()
            )
        }

        viewModel.result.observe(viewLifecycleOwner) { result ->
            binding.tilData.editText?.setText(result)
        }
    }

    private fun handleData(view: View, isEncrypt: Boolean) {
        dismissKeyboard(view.windowToken)

        val type = binding.spinnerEncryptType.selectedItem.toString()
        var data = ""
        var key = ""
        val transformation = binding.spinnerEncryptType.selectedItem.toString() + "/" +
                binding.spinnerEncryptPattern.selectedItem.toString() + "/" +
                binding.spinnerFilling.selectedItem.toString()
        var iv = ""
        context?.let { context ->
            binding.tilData.editText?.let { it ->
                if (it.text.isEmpty()) {
                    Toast.makeText(
                        context,
                        R.string.error_data_can_not_be_empty,
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                } else {
                    data = it.text.toString()
                }
            }
            binding.tilKey.editText?.let { it ->
                if (it.text.isEmpty()) {
                    Toast.makeText(
                        context,
                        R.string.error_password_can_not_be_empty,
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                } else {
                    key = it.text.toString()
                }
            }
            binding.tilIv.editText?.let { it ->
                if (it.text.isNotEmpty() && it.text.length != 16) {
                    Toast.makeText(
                        context,
                        R.string.error_iv_length_need_to_be_sixteen,
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                } else {
                    iv = it.text.toString()
                }
            }
        }

        // Return result
        when (isEncrypt) {
            true -> viewModel.encrypt(type, data, key, transformation, iv)
            false -> viewModel.decrypt(type, data, key, transformation, iv)
        }
    }
}