package ru.kartsev.dmitry.cinemadetails.mvvm.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.app_bar_default.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.ActivityViewImageBinding
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieImageViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.helper.DefaultPropertyHandler
import java.io.File
import javax.inject.Inject

class MovieImageActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    /** Section: Private Properties. */

    private lateinit var viewModel: MovieImageViewModel

    /** Section: Base Methods. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityViewImageBinding>(
            this,
            R.layout.activity_view_image
        )

        viewModel = getViewModel()
        binding.viewModel = viewModel

        setSupportActionBar(toolbarNoElevation)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowTitleEnabled(true)
        }

        if (savedInstanceState == null) {
            intent?.apply {
                if (!hasExtra(IMAGE_PATH) && !hasExtra(IMAGE_DIMENSIONS))  throw IllegalStateException()

                val imagePath = getStringExtra(IMAGE_PATH)
                supportActionBar?.title = getStringExtra(IMAGE_DIMENSIONS)
                viewModel.initializeByDefault(imagePath)
            }
        }

        propertyHandler.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_image, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    /** Section: Private Methods. */

    // TODO: Implement saving original file.
    private fun sendFileToExternalApp() {
        val file = File(viewModel.movieImageToSavePathUri).apply {
            if (!exists()) return
        }

        val uri = FileProvider.getUriForFile(this, "ru.kartsev.dmitry.cinemadetails.fileprovider", file)

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/*"
        }

        val packageManager = this.packageManager

        intent.resolveActivity(packageManager)?.also {
            startActivity(intent)
        }
    }

    /** Section: Property Handler. */

    private val propertyHandler = PropertyHandler(this)

    class PropertyHandler(
        reference: MovieImageActivity
    ) : DefaultPropertyHandler<MovieImageActivity>(reference) {
        override fun onPropertyChanged(reference: MovieImageActivity, propertyId: Int) = with(reference) {
            when (propertyId) {
                BR.action -> when (viewModel.action) {
                    else -> return@with
                }

                else -> return@with
            }
        }

        override fun observableOrNull(reference: MovieImageActivity) = reference.viewModel
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    companion object {
        private const val IMAGE_PATH = "IMAGE_PATH"
        private const val IMAGE_DIMENSIONS = "IMAGE_DIMENSIONS"

        fun openActivityByDefault(context: Context, imagePath: String, imageDimensions: String) {
            val intent = Intent(context, MovieImageActivity::class.java).apply {
                putExtra(IMAGE_PATH, imagePath)
                putExtra(IMAGE_DIMENSIONS, imageDimensions)
            }

            context.startActivity(intent)
        }
    }
}