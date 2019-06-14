package ru.kartsev.dmitry.cinemadetails.mvvm.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MainViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.MoviesListAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.DefaultPropertyHandler

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
//            viewModel.initializeByDefault()
        }

        observeLiveData()

        moviesAdapter = MoviesListAdapter(viewModel)
        mainViewRecyclerList.apply {
            val llm = LinearLayoutManager(context)
            layoutManager = llm
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

        propertyHandler.attach()
    }

    /** Section: Private Methods. */

    private fun observeLiveData() {
        viewModel.popularMovies.observe(this, Observer {
            moviesAdapter.submitList(it)
        })
    }

    /** Section: Property Handler. */

    private val propertyHandler = PropertyHandler(this)

    class PropertyHandler(
        reference: MainActivity
    ) : DefaultPropertyHandler<MainActivity>(reference) {
        override fun onPropertyChanged(reference: MainActivity, propertyId: Int) = with(reference) {
            when (propertyId) {
            }
        }

        override fun observableOrNull(reference: MainActivity) = reference.viewModel
    }
}
