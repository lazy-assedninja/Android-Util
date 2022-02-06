package me.lazy_assedninja.util.ui.util

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.lazy_assedninja.util.R
import me.lazy_assedninja.util.data.EventObserver
import me.lazy_assedninja.util.databinding.UtilFragmentBinding
import me.lazy_assedninja.util.library.ui.BaseFragment
import me.lazy_assedninja.util.library.util.AppExecutors
import me.lazy_assedninja.util.ui.index.MainActivity
import me.lazy_assedninja.util.util.autoCleared
import javax.inject.Inject

class UtilFragment : BaseFragment() {

    var binding by autoCleared<UtilFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: UtilViewModel by viewModels {
        viewModelFactory
    }

    private var adapter by autoCleared<UtilListAdapter>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Creates an instance of UtilComponent by grabbing the factory from the MainComponent
        // and injects this fragment
        (activity as MainActivity).mainComponent.utilComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UtilFragmentBinding.inflate(inflater, container, false).apply{
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
        adapter = UtilListAdapter(appExecutors, viewModel)
        binding.rv.adapter = adapter
    }

    private fun initData() {
        viewModel.openUtil.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                getString(R.string.navigation_encrypt_fragment) -> {
                    findNavController().navigate(
                        UtilFragmentDirections.toEncryptFragment()
                    )
                }
            }
        })
    }
}