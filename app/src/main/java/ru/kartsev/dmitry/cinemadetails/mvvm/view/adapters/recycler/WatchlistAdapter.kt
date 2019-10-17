package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.ItemMovieBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieListBaseViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper.DefaultDiffCallback
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.viewholder.ItemMovieViewHolder

class WatchlistAdapter(
    private val viewModel: MovieListBaseViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<MovieObservable> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.item_movie, parent, false
        )

        return ItemMovieViewHolder(binding as ItemMovieBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemMovieViewHolder).bind(viewModel, items[position])
    }

    override fun getItemCount(): Int = items.size


    fun updateItems(list: List<MovieObservable>) {
        val callback = DefaultDiffCallback(items, list)
        val result = DiffUtil.calculateDiff(callback)

        items.apply {
            clear()
            addAll(list)
        }

        result.dispatchUpdatesTo(this)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as ItemMovieViewHolder).cleanupImagesTask()
        super.onViewRecycled(holder)
    }
}