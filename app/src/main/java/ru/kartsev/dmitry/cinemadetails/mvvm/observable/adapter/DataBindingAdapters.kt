package ru.kartsev.dmitry.cinemadetails.mvvm.observable.adapter

import android.graphics.drawable.Drawable
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import java.io.File

object DataBindingAdapters : KoinComponent {
    /** Section: Adapters. */

    @JvmStatic
    @BindingAdapter(
        value = ["bind:image_uri", "bind:image_placeholder", "bind:image_error", "bind:image_center_inside", "bind:image_size"],
        requireAll = false
    )
    fun adapterImage(
        view: ImageView,
        uri: String?,
        defaultPlaceholder: Drawable? = null,
        errorPlaceholder: Drawable? = null,
        isCenterInside: Boolean = false,
        imageSize: String?
    ) {
        if (uri.isNullOrEmpty()) return

        val picasso = get<Picasso>()
        val size = imageSize ?: "w300"
        val creator = picasso.load("$size$uri")

        with(creator) {
            errorPlaceholder?.let {
                error(errorPlaceholder)
            }

            fit()
            if (isCenterInside) centerInside() else centerCrop()

            defaultPlaceholder?.let {
                placeholder(it)
            } ?: noPlaceholder()

            into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:viewVisibility")
    fun viewVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}