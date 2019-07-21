package ru.kartsev.dmitry.cinemadetails.mvvm.observable.adapter

import android.graphics.drawable.Drawable
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import org.koin.java.standalone.KoinJavaComponent.get
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import java.io.File
import java.lang.StringBuilder

/** Section: Adapters. */
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
    imageSize: String? = null
) {
    if (uri.isNullOrEmpty()) return

    val picasso = get(Picasso::class.java)
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

@BindingAdapter(
    value = ["bind:overall_info", "bind:overall_release_date", "bind:overall_runtime"],
    requireAll = true
)
fun formatOverallInfo(
    view: TextView,
    info: String?,
    releaseDate: String?,
    runtime: Int
) = with(view) {
    val util = get(Util::class.java)
    val string = StringBuilder(info ?: "")
    releaseDate?.let {
        string.append(
            " / ${
            resources.getString(
                R.string.date_release_year,
                util.formatTime(
                    it, "yyyy-MM-dd", "yyyy"
                )
            )}"
        )
    }
    val hours = runtime / 60
    val minutes = runtime % 60
    string.append(" / ${resources.getString(R.string.date_runtime, hours, minutes)}")

    text = string
}

@BindingAdapter("bind:finance")
fun formatFinance(view: TextView, sum: Long) = with(view) {
    val result = String.format("%,d", sum)
    text = if (sum > 0) {
        resources.getString(R.string.item_movie_finance, result)
    } else {
        resources.getString(R.string.item_movie_no_data)
    }
}

@BindingAdapter("bind:release_date_world")
fun viewReleaseDates(view: TextView, date: String) = with(view) {
    val util = get(Util::class.java)
    text = context.getString(
        R.string.date_release_world,
        util.formatTime(
            date, "yyyy-MM-dd", "dd MMMM yyyy"
        )
    )
}

@BindingAdapter("bind:viewVisibility")
fun viewVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}