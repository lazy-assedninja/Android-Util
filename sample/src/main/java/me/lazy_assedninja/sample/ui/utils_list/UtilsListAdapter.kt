package me.lazy_assedninja.sample.ui.utils_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.library_dagger.ui.BaseListAdapter
import me.lazy_assedninja.library_dagger.utils.ExecutorUtils
import me.lazy_assedninja.sample.R
import me.lazy_assedninja.sample.databinding.ItemUtilsBinding
import me.lazy_assedninja.sample.vo.Utils

class UtilsListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    executorUtils: ExecutorUtils,
    private val utilsClickCallback: ((Utils) -> Unit)?
) : BaseListAdapter<Utils, ItemUtilsBinding>(
    executorUtils = executorUtils,
    diffCallback = object : DiffUtil.ItemCallback<Utils>() {
        override fun areItemsTheSame(oldItem: Utils, newItem: Utils): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Utils, newItem: Utils): Boolean {
            return oldItem.name == newItem.name
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemUtilsBinding {
        val binding = DataBindingUtil.inflate<ItemUtilsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_utils,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.utils?.let {
                utilsClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: ItemUtilsBinding, item: Utils) {
        binding.utils = item
    }
}