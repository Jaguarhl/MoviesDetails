package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.ItemSimilarMovieBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.SimilarMovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieDetailsViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper.DefaultDiffCallback

class SimilarMoviesListAdapter(
    private val viewModel: MovieDetailsViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<SimilarMovieObservable> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(
            inflater, R.layout.item_similar_movie,
            parent, false
        ) as ItemSimilarMovieBinding

        return ItemSimilarMovieViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val observable = items[position]

        with(holder as ItemSimilarMovieViewHolder) {
            bind(observable, viewModel)
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as ItemSimilarMovieViewHolder).cleanupImagesTask()
        super.onViewRecycled(holder)
    }

    fun updateItems(list: List<SimilarMovieObservable>) {
        val callback = DefaultDiffCallback(items, list)
        val result = DiffUtil.calculateDiff(callback)

        items.apply {
            clear()
            addAll(list)
        }

        result.dispatchUpdatesTo(this)
    }

    class ItemSimilarMovieViewHolder(
        private val binding: ItemSimilarMovieBinding
    ) :
        RecyclerView.ViewHolder(binding.root), KoinComponent {
        fun bind(
            observable: SimilarMovieObservable,
            viewModel: MovieDetailsViewModel) {
            binding.baseObservable = observable
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        fun cleanupImagesTask() {
//            binding.itemSimilarMoviePoster.also {
//                Glide.with(it.context).pauseAllRequests()
//            }
        }
    }
}