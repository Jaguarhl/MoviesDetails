package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.koin.standalone.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MainViewModel
import ru.kartsev.dmitry.cinemadetails.databinding.ItemMovieBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper.DiffUtilCallBack

class MoviesListAdapter(
    private val viewModel: MainViewModel
) : PagedListAdapter<MovieObservable, MoviesListAdapter.ItemMovieViewHolder>(DiffUtilCallBack()),
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

    class ItemMovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            viewModel: MainViewModel,
            observable: MovieObservable
        ) {
            binding.viewModel = viewModel
            binding.baseObservable = observable
            binding.executePendingBindings()
        }
    }
}