package ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule.PICASSO_NAME
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.databinding.ItemMovieBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base.MovieListBaseViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.helper.DiffUtilCallBack
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.recycler.viewholder.ItemMovieViewHolder

class MoviesListAdapter(
    private val viewModel: MovieListBaseViewModel
) : PagedListAdapter<MovieObservable, ItemMovieViewHolder>(DiffUtilCallBack()),
    KoinComponent {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, R.layout.item_movie, parent, false
        )

        return ItemMovieViewHolder(binding as ItemMovieBinding)
    }

    override fun onBindViewHolder(holder: ItemMovieViewHolder, position: Int) {
        getItem(position)?.let {holder.bind(viewModel, it)}
    }

    override fun onViewRecycled(holder: ItemMovieViewHolder) {
        holder.cleanupImagesTask()
        super.onViewRecycled(holder)
    }
}