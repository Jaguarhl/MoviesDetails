package ru.kartsev.dmitry.cinemadetails.mvvm.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_details.*
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.ActivityDetailsBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieDetailsViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.GenresListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.VideoListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.DefaultPropertyHandler
import androidx.recyclerview.widget.LinearSnapHelper

class MovieDetailsActivity : AppCompatActivity() {
    lateinit var viewModel: MovieDetailsViewModel
    lateinit var genresAdapter: GenresListAdapter
    lateinit var videosAdapter: VideoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityDetailsBinding>(
            this,
            R.layout.activity_details
        )

        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)

        with(binding) {
            lifecycleOwner = this@MovieDetailsActivity
            viewModel = this@MovieDetailsActivity.viewModel
        }

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        genresAdapter = GenresListAdapter(viewModel)

        activityDetailsMovieGenresListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genresAdapter
        }

        videosAdapter = VideoListAdapter(lifecycle)

        activityDetailsMovieVideosListRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = videosAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }

        propertyHandler.attach()

        intent?.apply {
            if (!hasExtra(MOVIE_ID_KEY) || savedInstanceState != null) return@apply

            val movieId = getIntExtra(MOVIE_ID_KEY, 0)
            viewModel.initializeWithMovieId(movieId)
        }

        initListeners()
        observeLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_default, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /** Section: Private Methods. */

    private fun initListeners() {
        detailsScrollView
            ?.setOnScrollChangeListener { _: NestedScrollView?, _: Int, newScroll: Int, _: Int, oldScroll: Int ->
                if (oldScroll == 0 && newScroll > 0) app_bar_layout.setExpanded(false)
                else if (oldScroll > 0 && newScroll == 0) app_bar_layout.setExpanded(true)
            }

        app_bar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    viewModel.movieToolbarCollapsed = true
                } else if (viewModel.movieToolbarCollapsed) {
                    viewModel.movieToolbarCollapsed = false
                }
            }
        })
    }

    private fun observeLiveData() {
        viewModel.movieGenresLiveData.observe(this, Observer {
            genresAdapter.updateItems(it)
        })

        viewModel.movieVideosLiveData.observe(this, Observer {
            videosAdapter.updateItems(it)
        })
    }

    /** Section: Property Handler. */

    private val propertyHandler = PropertyHandler(this)

    class PropertyHandler(
        reference: MovieDetailsActivity
    ) : DefaultPropertyHandler<MovieDetailsActivity>(reference) {
        override fun onPropertyChanged(reference: MovieDetailsActivity, propertyId: Int) = with(reference) {
            when (propertyId) {
                BR.action -> when (viewModel.action) { }
            }
        }

        override fun observableOrNull(reference: MovieDetailsActivity) = reference.viewModel
    }

    companion object {
        private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

        fun openActivityWithMovieId(id: Int, context: Context) {
            val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID_KEY, id)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }

            context.startActivity(intent)
        }
    }
}