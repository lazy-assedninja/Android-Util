package me.lazy_assedninja.util.ui.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import me.lazy_assedninja.util.R
import me.lazy_assedninja.util.databinding.UtilItemBinding
import me.lazy_assedninja.util.library.ui.BaseListAdapter
import me.lazy_assedninja.util.library.util.AppExecutors

class UtilListAdapter(
    appExecutors: AppExecutors,
    private val viewModel: UtilViewModel,
) : BaseListAdapter<String, UtilItemBinding>(
    executorUtil = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): UtilItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.util_item,
            parent,
            false
        )
    }

    override fun bind(binding: UtilItemBinding, item: String) {
        binding.util = item
        binding.viewModel = viewModel
    }
}