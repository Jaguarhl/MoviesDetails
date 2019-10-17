package ru.kartsev.dmitry.cinemadetails.mvvm.view.image

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.app_bar_default.*
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.databinding.FragmentViewImageBinding
import java.io.File

class MovieImageFragment : Fragment() {

    /** Section: Private Properties. */

    private lateinit var viewModel: MovieImageViewModel
    private lateinit var binding: FragmentViewImageBinding
    private val args by navArgs<MovieImageFragmentArgs>()

    /** Section: Base Methods. */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate<FragmentViewImageBinding>(
            inflater,
            R.layout.fragment_view_image,
            container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MovieImageViewModel::class.java)

        binding.viewModel = viewModel

        activity?.let {
            it as AppCompatActivity
            it.setSupportActionBar(toolbarNoElevation)
            it.supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
                setDisplayShowTitleEnabled(true)
            }
        }

        args.apply {
            val imagePath = imageUrl
            val imageDimensions = imageDimensions
            activity?.also {
                (it as AppCompatActivity).supportActionBar?.title = imageDimensions
            }

            viewModel.initializeByDefault(imagePath)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_image, menu)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            navController().popBackStack()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    /** Section: Private Methods. */

    // TODO: Implement saving original file.
    private fun sendFileToExternalApp() {
        activity?.let {
            val file = File(viewModel.movieImageToSavePathUri).apply {
                if (!exists()) return
            }

            val uri = FileProvider.getUriForFile(
                it,
                "ru.kartsev.dmitry.cinemadetails.fileprovider",
                file
            )

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/*"
            }

            val packageManager = it.packageManager

            intent.resolveActivity(packageManager)?.also {
                startActivity(intent)
            }
        }
    }

    private fun navController() = findNavController()
}