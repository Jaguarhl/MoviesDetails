package ru.kartsev.dmitry.cinemadetails.mvvm.presentation.adapters.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.named
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule
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
        get<Picasso>(named(NetworkModule.PICASSO_NAME)).cancelRequest(binding.itemMoviePoster)
    }
}