package me.lazy_assedninja.sample.ui.utils_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import me.lazy_assedninja.library_dagger.ui.BaseFragment
import me.lazy_assedninja.library_dagger.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.binding.FragmentDataBindingComponent
import me.lazy_assedninja.sample.databinding.FragmentUtilsBinding
import me.lazy_assedninja.sample.ui.index.MainActivity
import me.lazy_assedninja.sample.utils.autoCleared
import javax.inject.Inject

class UtilsFragment : BaseFragment() {

    var binding by autoCleared<FragmentUtilsBinding>()
    private var adapter by autoCleared<UtilsListAdapter>()

    @Inject
    lateinit var executorUtils: ExecutorUtils

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: UtilsViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Grabs the mainComponent from the Activity and injects this Fragment
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_utils,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadUtilsList()

        adapter = UtilsListAdapter(dataBindingComponent, executorUtils) {
            when (it.name) {
                getString(R.string.title_encrypt_utils_demo) -> {
                    findNavController().navigate(
                        UtilsFragmentDirections.toEncryptFragment()
                    )
                }
            }
        }
        binding.utilsList.adapter = adapter

        initNavigationUI()
        initUtilsList(viewModel)
    }

    private fun initNavigationUI() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }

    private fun initUtilsList(viewModel: UtilsViewModel) {
        viewModel.utilsList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            viewModel.isLoading.set(false)
        })
    }
}