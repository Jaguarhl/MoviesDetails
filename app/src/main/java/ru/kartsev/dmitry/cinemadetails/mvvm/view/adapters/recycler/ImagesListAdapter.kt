package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.ItemMovieImageBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.ImageObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieDetailsViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper.DefaultDiffCallback

class ImagesListAdapter(
    private val viewModel: MovieDetailsViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<ImageObservable> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(
            inflater, R.layout.item_movie_image,
            parent, false
        ) as ItemMovieImageBinding

        return ItemMovieImageViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val observable = items[position]

        with(holder as ItemMovieImageViewHolder) {
            bind(observable, viewModel)
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as ItemMovieImageViewHolder).cleanupImagesTask()
        super.onViewRecycled(holder)
    }

    fun updateItems(list: List<ImageObservable>) {
        val callback = DefaultDiffCallback(items, list)
        val result = DiffUtil.calculateDiff(callback)

        items.apply {
            clear()
            addAll(list)
        }

        result.dispatchUpdatesTo(this)
    }

    class ItemMovieImageViewHolder(
        private val binding: ItemMovieImageBinding
    ) :
        RecyclerView.ViewHolder(binding.root), KoinComponent {
        fun bind(
            observable: ImageObservable,
            viewModel: MovieDetailsViewModel) {
            binding.baseObservable = observable
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        fun cleanupImagesTask() {
//            binding.itemMovieImagePoster.also {
//                Glide.with(it.context).pauseAllRequests()
//            }
        }
    }
}