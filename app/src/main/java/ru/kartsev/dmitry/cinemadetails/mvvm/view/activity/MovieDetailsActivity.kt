package ru.kartsev.dmitry.cinemadetails.mvvm.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.app_bar_default.*
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.ActivityDetailsBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieDetailsViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.DefaultPropertyHandler

class MovieDetailsActivity : AppCompatActivity() {
    lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityDetailsBinding>(
            this,
            R.layout.activity_details
        )

        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)

        with (binding) {
            lifecycleOwner = this@MovieDetailsActivity
            viewModel = this@MovieDetailsActivity.viewModel
        }

        setSupportActionBar(toolbarDefault)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        propertyHandler.attach()

        intent?.apply {
            if (!hasExtra(MOVIE_ID_KEY)) return@apply

            val movieId = getIntExtra(MOVIE_ID_KEY, 0)
            viewModel.initializeWithMovieId(movieId)
        }
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

    /** Section: Property Handler. */

    private val propertyHandler = PropertyHandler(this)

    class PropertyHandler(
        reference: MovieDetailsActivity
    ) : DefaultPropertyHandler<MovieDetailsActivity>(reference) {
        override fun onPropertyChanged(reference: MovieDetailsActivity, propertyId: Int) = with(reference) {
            when (propertyId) {
            }
        }

        override fun observableOrNull(reference: MovieDetailsActivity) = reference.viewModel
    }

    companion object {
        private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

        fun openActivityWithMovieId(id: Int, context: Context) {
            val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID_KEY, id)
            }

            context.startActivity(intent)
        }
    }
}