package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.base

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import ru.kartsev.dmitry.cinemadetails.databinding.ItemLoadingBinding

abstract class BaseAdapterPagination<T> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var dataList: MutableList<T> = mutableListOf()
    private var loadingFooter: T? = null
    protected var isLoadingAdded = false

    val isEmpty: Boolean
        get() = itemCount == 0

    fun getList(): List<T> {
        return dataList
    }

    fun setList(data: List<T>) {
        with (dataList) {
            clear()
            addAll(data)
        }

        notifyDataSetChanged()
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun getItem(position: Int): T {
        return dataList[position]
    }

    /*
       Helpers
    */

    fun setLoadingFooter(loadingFooter: T) {
        this.loadingFooter = loadingFooter
    }

    fun add(item: T) {
        dataList.add(item)
    }

    fun addAll(itemsList: List<T>) {
        for (item in itemsList) {
            add(item)
        }
        notifyDataSetChanged()
    }

    fun remove(item: T) {
        val position = dataList.indexOf(item)
        if (position >= 0) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        dataList.add(loadingFooter!!)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        if (dataList.contains(loadingFooter)) {
            val position = dataList.indexOf(loadingFooter)
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     * ILoading ViewHolder
     */

    protected inner class ItemLoadingViewHolder (
        private val binding: ItemLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
        ) {
            binding.executePendingBindings()
        }
    }
}