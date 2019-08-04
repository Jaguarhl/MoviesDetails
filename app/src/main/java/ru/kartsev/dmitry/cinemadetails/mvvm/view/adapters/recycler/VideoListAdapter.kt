package ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.VideoObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.helper.DefaultDiffCallback

class VideoListAdapter(
    private val lifecycle: Lifecycle
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<VideoObservable> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val youTubePlayerView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false) as YouTubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        return ItemVideoViewHolder(
            youTubePlayerView
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val observable = items[position]

        with(holder as ItemVideoViewHolder) {
            cueVideo(observable.key)
        }
    }

    fun updateItems(list: List<VideoObservable>) {
        val callback = DefaultDiffCallback(items, list)
        val result = DiffUtil.calculateDiff(callback)

        items.apply {
            clear()
            addAll(list)
        }

        result.dispatchUpdatesTo(this)
    }

    internal class ItemVideoViewHolder(private val youTubePlayerView: YouTubePlayerView) :
        RecyclerView.ViewHolder(youTubePlayerView) {
        private var youTubePlayer: YouTubePlayer? = null
        private var currentVideoId: String? = null

        init {
            youTubePlayerView.apply {
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(initializedYouTubePlayer: YouTubePlayer) {
                        youTubePlayer = initializedYouTubePlayer
                        youTubePlayer!!.cueVideo(currentVideoId!!, 0f)
                    }
                })

                addFullScreenListener(object : YouTubePlayerFullScreenListener{
                    override fun onYouTubePlayerEnterFullScreen() {
                        // FIXME: Implement open video in fullscreen mode
                    }

                    override fun onYouTubePlayerExitFullScreen() {
                        // Nothing to do here
                    }
                })
            }
        }

        fun cueVideo(videoId: String) {
            currentVideoId = videoId

            if (youTubePlayer == null)
                return

            youTubePlayer!!.cueVideo(videoId, 0f)
        }
    }
}