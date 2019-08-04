package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule.PICASSO_NAME
import ru.kartsev.dmitry.cinemadetails.databinding.ItemCastBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.CastObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieDetailsViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper.DefaultDiffCallback

class CreditsCastListAdapter(
    private val viewModel: MovieDetailsViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<CastObservable> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(
            inflater, R.layout.item_credits,
            parent, false
        ) as ItemCastBinding

        return ItemCastViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val observable = items[position]

        with(holder as ItemCastViewHolder) {
            bind(observable, viewModel)
        }
    }

    fun updateItems(list: List<CastObservable>) {
        val callback = DefaultDiffCallback(items, list)
        val result = DiffUtil.calculateDiff(callback)

        items.apply {
            clear()
            addAll(list)
        }

        result.dispatchUpdatesTo(this)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder as ItemCastViewHolder).cleanupImagesTask()
        super.onViewRecycled(holder)
    }

    class ItemCastViewHolder(
        private val binding: ItemCastBinding
    ) :
        RecyclerView.ViewHolder(binding.root), KoinComponent {
        fun bind(
            observable: CastObservable,
            viewModel: MovieDetailsViewModel
        ) {
            binding.baseObservable = observable
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        fun cleanupImagesTask() {
            get<Picasso>(PICASSO_NAME).cancelRequest(binding.itemCreditsPersonPhoto)
        }
    }
}