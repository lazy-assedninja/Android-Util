package me.lazy_assedninja.util.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import me.lazy_assedninja.util.ui.util.UtilListAdapter

/**
 * Data Binding adapters specific to the app.
 */
@Suppress("unused")
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("showOrHide")
    fun showOrHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("util_items")
    fun setUtilItems(listView: RecyclerView, items: List<String>?) {
        (listView.adapter as UtilListAdapter).submitList(items)
    }
}