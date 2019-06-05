package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.koin.standalone.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MainViewModel
import ru.kartsev.dmitry.cinemadetails.databinding.ItemMovieBinding

class MoviesListAdapter(
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    KoinComponent {

    var items = mutableListOf<MovieObservable>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, viewType, parent, false
        )

        return ItemMovieViewHolder(binding as ItemMovieBinding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_movie
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val observable = items[position]

        (holder as ItemMovieViewHolder).bind(viewModel, observable)
    }

    fun initList(data: List<MovieObservable>) {
        with (items) {
            clear()
            addAll(data)
        }

        notifyDataSetChanged()
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