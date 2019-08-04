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
import kotlinx.android.synthetic.main.fragment_now_playing.*
import org.koin.standalone.KoinComponent
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.FragmentNowPlayingBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.NowPlayingViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.NowPlayingViewModel.Companion.ACTION_OPEN_DETAILS
import ru.kartsev.dmitry.cinemadetails.mvvm.view.activity.MovieDetailsActivity
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.MoviesListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.DefaultPropertyHandler

class NowPlayingFragment : Fragment(), KoinComponent {
    /** Section: Static functions. */

    companion object {
        fun newInstance(): NowPlayingFragment = NowPlayingFragment()
    }

    /** Section: Private fields. */

    private lateinit var viewModel: NowPlayingViewModel
    private lateinit var moviesAdapter: MoviesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.inflate<FragmentNowPlayingBinding>(
            inflater,
            R.layout.fragment_now_playing,
            container, false)

        viewModel = ViewModelProviders.of(this).get(NowPlayingViewModel::class.java)

        binding.viewModel = viewModel

        propertyHandler.attach()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.apply {
            moviesAdapter = MoviesListAdapter(viewModel)
            mainViewRecyclerList.apply {
                val llm = LinearLayoutManager(context)
                layoutManager = llm
                setHasFixedSize(true)
                adapter = moviesAdapter
            }

            observeLiveData()
        }

//        activity?.apply {
//            setTitle(R.string.contact_list_toolbar_title)
//        }
//
//        if (savedInstanceState == null) {
//            viewModel.initializeDefault()
//            viewModel.attemptLoadContactsRecommendations()
//        } else {
//            allContactsActiveAdapter.updateInitialized(listWithHeader())
//        }
    }

    /** Section: Private Methods. */

    private fun observeLiveData() {
        viewModel.popularMovies.observe(this, Observer {
            moviesAdapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        mainViewRecyclerList.adapter = null
        propertyHandler.detach()
        super.onDestroyView()
    }

    /** Section: Property Handler. */

    private val propertyHandler = PropertyHandler(this)

    class PropertyHandler(
        reference: NowPlayingFragment
    ) : DefaultPropertyHandler<NowPlayingFragment>(reference) {
        override fun onPropertyChanged(reference: NowPlayingFragment, propertyId: Int) = with(reference) {
            val context = context ?: return
            when (propertyId) {
                ru.kartsev.dmitry.cinemadetails.BR.action -> when (viewModel.action) {
                    ACTION_OPEN_DETAILS -> MovieDetailsActivity.openActivityWithMovieId(viewModel.movieIdToOpenDetails!!, context)
                }
            }
        }

        override fun observableOrNull(reference: NowPlayingFragment) = reference.viewModel
    }
}