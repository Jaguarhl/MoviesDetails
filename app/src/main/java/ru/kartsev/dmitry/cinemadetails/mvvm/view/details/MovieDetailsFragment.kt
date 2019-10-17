package ru.kartsev.dmitry.cinemadetails.mvvm.view.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_movie_details.*
import androidx.recyclerview.widget.LinearSnapHelper
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.GenreObservable
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.KeywordObservable
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.FragmentMovieDetailsBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.CreditsCastListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.ImagesListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.SimilarMoviesListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler.VideoListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.autoCleared

class MovieDetailsFragment : Fragment() {

    var binding by autoCleared<FragmentMovieDetailsBinding>()

    /** Section: Private Properties. */

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var castAdapter: CreditsCastListAdapter
    private lateinit var videosAdapter: VideoListAdapter
    private lateinit var similarMoviesListAdapter: SimilarMoviesListAdapter
    private lateinit var movieImagesListAdapter: ImagesListAdapter
    private val args by navArgs<MovieDetailsFragmentArgs>()

    /** Section: Base Methods. */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie_details, container,
            false
        )
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@MovieDetailsFragment.viewLifecycleOwner
            viewModel = this@MovieDetailsFragment.viewModel
        }

        initRecyclerViews()

        initListeners()
        observeLiveData()

        activity?.let {it as AppCompatActivity
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
                setHomeButtonEnabled(true)
            }
        }

        args.apply {
            val movieId = movieId
            viewModel.initializeWithMovieId(movieId)
        }
    }

    /** Section: Private Methods. */

    private fun initRecyclerViews() {
        castAdapter = CreditsCastListAdapter(viewModel)

        fragmentDetailsMovieCastListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = castAdapter
        }

        similarMoviesListAdapter =
            SimilarMoviesListAdapter(viewModel)

        fragmentDetailsMovieSimilarListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = similarMoviesListAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }

        videosAdapter = VideoListAdapter(lifecycle)

        fragmentDetailsMovieVideosListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = videosAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }

        movieImagesListAdapter = ImagesListAdapter(viewModel)

        fragmentDetailsMovieImagesListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = movieImagesListAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    override fun onDestroy() {
        fragmentDetailsMovieCastListRecycler?.adapter = null
        fragmentDetailsMovieSimilarListRecycler?.adapter = null
        fragmentDetailsMovieVideosListRecycler?.adapter = null
        fragmentDetailsMovieImagesListRecycler?.adapter = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_details, menu)
        val item = menu.findItem(R.id.menu_item_add_to_favourite)

        item?.let {
            val iconRes = if (!viewModel.movieAddedToFavourites) R.drawable.ic_favourite_border_white else R.drawable.ic_favourite_white
            it.setIcon(iconRes)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
            android.R.id.home -> {
                navController().popBackStack()
                true
            }

            R.id.menu_item_search -> {
                // FIXME: Replace by search activity opening.
                viewModel.exceptionLiveData.postValue(getString(R.string.warning_coming_soon))
                true
            }

            R.id.menu_item_send -> {
                shareMovie()
                true
            }

            R.id.menu_item_add_to_favourite -> {
                viewModel.addMovieToFavourites()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /** Section: Private Methods. */

    private fun initListeners() {
        app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    viewModel.movieToolbarCollapsed.postValue(true)
                } else if (viewModel.movieToolbarCollapsed.value != null && viewModel.movieToolbarCollapsed.value!!) {
                    viewModel.movieToolbarCollapsed.postValue(false)
                }
            }
        })
    }

    private fun observeLiveData() {
        viewModel.movieGenresLiveData.observe(this, Observer {
            addGenresChips(it)
        })

        viewModel.movieVideosLiveData.observe(this, Observer {
            videosAdapter.updateItems(it)
        })

        viewModel.movieKeywordsLiveData.observe(this, Observer {
            addKeywordsChips(it)
        })

        viewModel.movieCreditsCastLiveData.observe(this, Observer {
            castAdapter.updateItems(it)
        })

        viewModel.movieSimilarMoviesLiveData.observe(this, Observer {
            similarMoviesListAdapter.updateItems(it)
        })

        viewModel.movieImagesLiveData.observe(this, Observer {
            movieImagesListAdapter.updateItems(it)
        })

        viewModel.movieNoDataAvailable.observe(this, Observer {
            fragmentDetailsMovieNoData.visibility = if (it) View.VISIBLE else View.GONE
            fragmentDetailsMovieNoDataBackButton.setOnClickListener {
                navController().popBackStack()
            }
        })

        viewModel.exceptionLiveData.observe(this, Observer {
            Snackbar.make(fragmentRoot, it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.movieDetailsUIEvents.observe(this, Observer {
            when (it) {
                is CollapseToolbarEvent -> {
                    ViewCompat.setNestedScrollingEnabled(detailsScrollView, false)
                    app_bar_layout?.apply {
                        setExpanded(false, false)
                        (layoutParams as CoordinatorLayout.LayoutParams).behavior?.also { coordinator ->
                            (coordinator as AppBarLayout.Behavior).setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                                override fun canDrag(p0: AppBarLayout): Boolean {
                                    return false
                                }
                            })
                        }
                    }
                    viewModel.movieToolbarCollapsed.postValue(false)
                }

                is MarkFavouriteEvent -> activity?.invalidateOptionsMenu()

                is OpenImageEvent -> navController().navigate(MovieDetailsFragmentDirections.showMovieImage(it.imagePath, it.imageDimensions))
            }
        })
    }

    private fun shareMovie() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            val stringToShare = viewModel.getMovieInfoToShare(
                resources.getString(
                    R.string.activity_movie_details_share_title,
                    viewModel.movieTitle
                ), resources.getString(R.string.activity_movie_details_release_date_label)
                , resources.getString(
                    R.string.activity_movie_details_genres_label
                )
            )
            putExtra(Intent.EXTRA_TEXT, stringToShare)
            type = "text/plain"
        }

        startActivity(
            Intent.createChooser(
                shareIntent,
                resources.getText(R.string.activity_movie_details_application_chooser_title)
            )
        )
    }

    /** Section: Chips Workaround. */

    private fun addKeywordsChips(list: List<KeywordObservable>?) {
        with(fragmentDetailsMovieKeywordsListGroup) {
            removeAllViews()
            list?.forEach {
                addView(getChip(it.name, it.id))
            }
        }
    }

    private fun addGenresChips(list: List<GenreObservable>?) {
        with(fragmentDetailsMovieGenresListGroup) {
            removeAllViews()
            list?.forEach {
                addView(getChip(it.name, it.id))
            }
        }
    }

    private fun getChip(textToSet: String, genreId: Int): Chip {
        return Chip(activity).apply {
            setChipDrawable(ChipDrawable.createFromResource(context, R.xml.item_chip))
            text = textToSet
            setOnClickListener {
                // FIXME: Implement click action
                Toast.makeText(context, "Chip with id: $genreId clicked.", Toast.LENGTH_LONG).show()
            }
        }
    }

    /** Section: Property Handler. */

    /*private val propertyHandler =
        PropertyHandler(
            this
        )

    class PropertyHandler(
        reference: MovieDetailsFragment
    ) : DefaultPropertyHandler<MovieDetailsFragment>(reference) {
        override fun onPropertyChanged(reference: MovieDetailsFragment, propertyId: Int) = with(reference) {
            when (propertyId) {
                BR.action -> when (viewModel.action) {
                    ACTION_OPEN_MOVIE -> viewModel.movieIdToShow?.let {
                        openActivityWithMovieId(
                            it,
                            this
                        )
                    }

                    ACTION_COLLAPSE_TOOLBAR -> {
                        ViewCompat.setNestedScrollingEnabled(detailsScrollView, false)
                        app_bar_layout?.apply {
                            setExpanded(false, false)
                            (layoutParams as CoordinatorLayout.LayoutParams).behavior?.also {
                                (it as AppBarLayout.Behavior).setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                                    override fun canDrag(p0: AppBarLayout): Boolean {
                                        return false
                                    }
                                })
                            }
                        }
                        viewModel.movieToolbarCollapsed = false
                    }

                    ACTION_OPEN_IMAGE -> MovieImageActivity.openActivityByDefault(
                        this,
                        viewModel.movieImagePathToOpen!!,
                        viewModel.movieImageDimensionsToOpen!!
                    )

                    ACTION_MARK_FAVOURITE -> invalidateOptionsMenu()

                    else -> return@with
                }

                else -> return@with
            }
        }

        override fun observableOrNull(reference: MovieDetailsFragment) = reference.viewModel
    }*/

    private fun navController() = findNavController()
}