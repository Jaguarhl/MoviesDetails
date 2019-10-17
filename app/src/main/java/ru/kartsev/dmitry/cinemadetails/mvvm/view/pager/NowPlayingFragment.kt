package ru.kartsev.dmitry.cinemadetails.mvvm.view.pager

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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.FragmentNowPlayingBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.MoviesListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.autoCleared

class NowPlayingFragment : Fragment(), KoinComponent {
    /** Section: Static functions. */

    companion object {
        fun newInstance(): NowPlayingFragment =
            NowPlayingFragment()
    }

    var binding by autoCleared<FragmentNowPlayingBinding>()

    /** Section: Private fields. */

    private val viewModel: MainFragmentViewModel by lazy { parentFragment!!.viewModel<MainFragmentViewModel>().value }
    private lateinit var moviesAdapter: MoviesListAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    internal var callback: OnItemSelectedCallback? = null

    fun setOnItemSelectedListener(callback: OnItemSelectedCallback) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate<FragmentNowPlayingBinding>(
            inflater,
            R.layout.fragment_now_playing,
            container, false
        )

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.initializeNowPlaying()

        activity?.apply {
            moviesAdapter = MoviesListAdapter(viewModel)
            nowPlayingFragmentRecyclerList.apply {
                val llm = LinearLayoutManager(context)
                layoutManager = llm
                setHasFixedSize(true)
                adapter = moviesAdapter
            }

            observeLiveData()
            setTitle(R.string.activity_main_tab_now_playing_title)
        }
    }

    override fun onDestroyView() {
        nowPlayingFragmentRecyclerList.adapter = null
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

        viewModel.exceptionLiveData.observe(this, Observer {
            Snackbar.make(rootView, it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.moviesListEmpty.observe(this, Observer {
            nowPlayingFragmentNoData.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.movieUIEvents.observe(this, Observer {
            when (it) {
                is ShowMovieDetails -> callback?.onItemSelected(it.movieId)
            }
        })
    }

    private fun initListeners() {
        swipeRefresh.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Default) {
                viewModel.refreshData()
            }
        }
    }
}