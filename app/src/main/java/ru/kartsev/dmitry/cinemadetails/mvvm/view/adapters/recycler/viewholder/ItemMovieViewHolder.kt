package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.databinding.ItemMovieBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.MovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.base.MovieListBaseViewModel

class ItemMovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root), KoinComponent {
    fun bind(
        viewModel: MovieListBaseViewModel,
        observable: MovieObservable
    ) {
        binding.viewModel = viewModel
        binding.baseObservable = observable
        binding.executePendingBindings()
    }

    fun cleanupImagesTask() {
//        binding.itemMoviePoster.also {
//            Glide.with(it.context).pauseAllRequests()
//        }
    }
}