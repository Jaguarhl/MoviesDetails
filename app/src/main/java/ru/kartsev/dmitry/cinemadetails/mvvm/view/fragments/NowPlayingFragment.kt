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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.databinding.FragmentNowPlayingBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.NowPlayingViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.NowPlayingViewModel.Companion.ACTION_OPEN_DETAILS
import ru.kartsev.dmitry.cinemadetails.mvvm.view.activity.MovieDetailsActivity
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.MoviesListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.DefaultPropertyHandler

class NowPlayingFragment : Fragment(), KoinComponent {
    /** Section: Static functions. */

    companion object {
        const val SWIPE_REFRESH_DELAY = 1000L

        fun newInstance(): NowPlayingFragment = NowPlayingFragment()
    }

    /** Section: Private fields. */

    private lateinit var viewModel: NowPlayingViewModel
    private lateinit var moviesAdapter: MoviesListAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.inflate<FragmentNowPlayingBinding>(
            inflater,
            R.layout.fragment_now_playing,
            container, false
        )

        viewModel = ViewModelProviders.of(this).get(NowPlayingViewModel::class.java)
        viewModel.initializeByDefault()

        binding.viewModel = viewModel

        propertyHandler.attach()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.apply {
            moviesAdapter = MoviesListAdapter(viewModel)
            nowPlayingFragmentRecyclerList.apply {
                val llm = LinearLayoutManager(context)
                layoutManager = llm
                setHasFixedSize(true)
                adapter = moviesAdapter
            }

            observeLiveData()
        }


        activity?.apply {
            setTitle(R.string.activity_main_tab_now_playing_title)
        }
    }

    override fun onDestroyView() {
        nowPlayingFragmentRecyclerList.adapter = null
        propertyHandler.detach()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipeRefresh = view.findViewById(R.id.nowPlayingFragmentSwipeToRefreshLayout)
        initListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    /** Section: Private Methods. */

    private fun observeLiveData() {
        viewModel.nowPlayingMovies.observe(this, Observer {
            moviesAdapter.submitList(it)
            swipeRefresh.isRefreshing = it.isNotEmpty()
        })
    }

    private fun initListeners() {
        swipeRefresh.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Default) {
                viewModel.refreshData()
            }
        }
    }

    /** Section: Property Handler. */

    private val propertyHandler = PropertyHandler(this)

    class PropertyHandler(
        reference: NowPlayingFragment
    ) : DefaultPropertyHandler<NowPlayingFragment>(reference) {
        override fun onPropertyChanged(reference: NowPlayingFragment, propertyId: Int) =
            with(reference) {
                val context = context ?: return
                when (propertyId) {
                    BR.action -> when (viewModel.action) {
                        ACTION_OPEN_DETAILS -> MovieDetailsActivity.openActivityWithMovieId(
                            viewModel.movieIdToOpenDetails!!,
                            context
                        )
                    }
                }
            }

        override fun observableOrNull(reference: NowPlayingFragment) = reference.viewModel
    }
}