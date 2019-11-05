package ru.kartsev.dmitry.cinemadetails.mvvm.observable.adapter

import android.graphics.Typeface.ITALIC
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.java.KoinJavaComponent.get
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import ru.kartsev.dmitry.cinemadetails.common.utils.generateImageLink
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import java.lang.StringBuilder

/** Section: Adapters. */
@BindingAdapter(
    value = ["app:image_uri", "app:image_placeholder", "app:image_error", "app:image_center_inside", "app:image_size", "app:image_zoom"],
    requireAll = false
)
fun adapterImage(
    view: ImageView,
    uri: String?,
    defaultPlaceholder: Drawable? = null,
    errorPlaceholder: Drawable? = null,
    isCenterInside: Boolean = false,
    imageSize: String? = null,
    imageZoomable: Boolean = false
) {
    if (uri.isNullOrEmpty()) return
    val url = "${imageSize.toString()}${uri.toString()}".generateImageLink(
        get(TmdbSettingsRepository::class.java).imagesBaseUrl)

    val creator = Glide.with(view.context).load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    with(creator) {
        errorPlaceholder?.let {
            error(errorPlaceholder)
        }

        if (isCenterInside && !imageZoomable) centerInside() else fitCenter()

        defaultPlaceholder?.let {
            placeholder(it)
        }

        into(view)
    }
}

@BindingAdapter(
    value = ["app:overall_info", "app:overall_release_date", "app:overall_runtime"],
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

@BindingAdapter("app:finance")
fun formatFinance(view: TextView, sum: Long) = with(view) {
    val result = String.format("%,d", sum)
    text = if (sum > 0) {
        resources.getString(R.string.activity_movie_details_finance, result)
    } else {
        resources.getString(R.string.activity_movie_details_no_data)
    }
}

@BindingAdapter("app:original_title")
fun movieOriginalTitle(view: TextView, title: String) = with(view) {
    val label = context.getString(R.string.activity_movie_details_title_original_label)
    // FIXME: Replace spaces by something more appropriate and tunable
    val finalText = "$label   $title"
    val spannableString = SpannableString(finalText).apply {
        setSpan(
            StyleSpan(ITALIC),
            label.length,
            length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }
    text = spannableString
}

@BindingAdapter("app:similar_movies_label")
fun labelSimilarMovies(view: TextView, count: Int?) = with(view) {
    count?.let {
        text = resources.getString(R.string.activity_movie_details_similar_label, it)
    }
}

@BindingAdapter("app:movie_videos_label")
fun labelMovieVideos(view: TextView, count: Int?) = with(view) {
    count?.let {
        text = resources.getString(R.string.activity_movie_details_videos_label, it)
    }
}

@BindingAdapter("app:movie_images_label")
fun labelMovieImages(view: TextView, count: Int?) = with(view) {
    count?.let {
        text = resources.getString(R.string.activity_movie_details_images_label, it)
    }
}

@BindingAdapter("app:release_date_world")
fun viewReleaseDates(view: TextView, date: String) = with(view) {
    val util = get(Util::class.java)
    text = context.getString(
        R.string.date_release_world,
        util.formatTime(
            date, "yyyy-MM-dd", "dd MMMM yyyy"
        )
    )
}

@BindingAdapter("app:viewVisibility")
fun viewVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}