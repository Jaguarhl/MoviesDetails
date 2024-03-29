package ru.kartsev.dmitry.cinemadetails.mvvm.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_watchlist.*
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.databinding.FragmentWatchlistBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.WatchlistViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.WatchlistViewModel.Companion.ACTION_OPEN_DETAILS
import ru.kartsev.dmitry.cinemadetails.mvvm.view.activity.MovieDetailsActivity
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.WatchlistAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.DefaultPropertyHandler

class WatchlistFragment : Fragment(), KoinComponent {

    /** Section: Static functions. */

    companion object {
        fun newInstance(): WatchlistFragment = WatchlistFragment()
    }

    /** Section: Private fields. */

    private lateinit var viewModel: WatchlistViewModel
    private lateinit var watchlistAdapter: WatchlistAdapter
    private lateinit var viewBinding: FragmentWatchlistBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        viewBinding = DataBindingUtil.inflate<FragmentWatchlistBinding>(
            inflater,
            R.layout.fragment_watchlist,
            container, false)

        viewModel = ViewModelProviders.of(this).get(WatchlistViewModel::class.java)

        viewModel.initializeByDefault()

        propertyHandler.attach()
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
        }

        activity?.apply {
            watchlistAdapter = WatchlistAdapter(viewModel)
            fragmentWatchlistRecyclerList.apply {
                val llm = LinearLayoutManager(context)
                layoutManager = llm
                setHasFixedSize(true)
                adapter = watchlistAdapter
            }

            observeLiveData()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavourites()
    }

    /** Section: Private Methods. */

    private fun observeLiveData() {
        viewModel.watchlistMovies.observe(this, Observer {
            watchlistAdapter.updateItems(it)
        })

        viewModel.moviesListEmpty.observe(this, Observer {
            fragmentWatchlistEmptyHolder.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onDestroyView() {
        fragmentWatchlistRecyclerList.adapter = null
        propertyHandler.detach()
        super.onDestroyView()
    }

    /** Section: Property Handler. */

    private val propertyHandler = PropertyHandler(this)

    class PropertyHandler(
        reference: WatchlistFragment
    ) : DefaultPropertyHandler<WatchlistFragment>(reference) {
        override fun onPropertyChanged(reference: WatchlistFragment, propertyId: Int) = with(reference) {
            val context = context ?: return
            when (propertyId) {
                BR.action -> when (viewModel.action) {
                    ACTION_OPEN_DETAILS -> MovieDetailsActivity.openActivityWithMovieId(viewModel.movieIdToOpenDetails!!, context)
                }
            }
        }

        override fun observableOrNull(reference: WatchlistFragment) = reference.viewModel
    }
}