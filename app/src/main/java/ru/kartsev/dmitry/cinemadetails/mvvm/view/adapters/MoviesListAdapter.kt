package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.koin.standalone.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.ItemLoadingBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MainViewModel
import ru.kartsev.dmitry.cinemadetails.databinding.ItemMovieBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.base.BaseAdapterPagination

class MoviesListAdapter(
    private val viewModel: MainViewModel
) : BaseAdapterPagination<MovieObservable>(),
    KoinComponent {

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1 && isLoadingAdded) R.layout.item_loading else R.layout.item_movie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, viewType, parent, false
        )

        return when (viewType) {
            R.layout.item_loading -> ItemLoadingViewHolder(binding as ItemLoadingBinding)
            else -> ItemMovieViewHolder(binding as ItemMovieBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie -> {
                val observable = dataList[position]

                (holder as ItemMovieViewHolder).bind(viewModel, observable)
            }

            R.layout.item_loading -> (holder as BaseAdapterPagination<*>.ItemLoadingViewHolder).bind()
        }
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