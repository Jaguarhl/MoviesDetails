package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.config.AppConfig.LANGUAGE
import ru.kartsev.dmitry.cinemadetails.common.config.AppConfig.MAX_CAST_ORDER
import ru.kartsev.dmitry.cinemadetails.common.config.AppConfig.MAX_SIMILAR_MOVIES
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.credits.Cast
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieGenre
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.keywords.Keyword
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.MovieEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.videos.MovieVideo
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.CastObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.GenreObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.KeywordObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.SimilarMovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.VideoObservable
import kotlin.coroutines.CoroutineContext

class MovieDetailsViewModel : ObservableViewModel(), KoinComponent {
    /** Section: Injections. */

    private val movieRepository: MovieRepository by inject()

    /** Section: Bindable Properties. */

    var action: Int? = null
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.action)
        }

    var loading: Boolean = false
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.loading)
        }

    var movieVideoEnabled: Boolean = false
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieVideoEnabled)
        }

    var movieSimilarMoviesEnabled: Boolean = false
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieSimilarMoviesEnabled)
        }

    var movieToolbarCollapsed: Boolean = false
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieToolbarCollapsed)
        }

    var movieTitle: String = ""
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieTitle)
        }

    var movieTitleOriginal: String = ""
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieTitleOriginal)
        }

    var movieDescription: String = ""
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieDescription)
        }

    var moviePosterPath: String = ""
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.moviePosterPath)
        }

    var movieBackdropPath: String = ""
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieBackdropPath)
        }

    var movieOverallInfo: String = ""
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieOverallInfo)
        }

    var movieReleaseDate: String = ""
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieReleaseDate)
        }

    var movieRuntime: Int = 0
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieRuntime)
        }

    var moviePopularity: Double = 0.0
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.moviePopularity)
        }

    var movieBudget: Long = 0
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieBudget)
        }

    var movieRevenue: Long = 0
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieRevenue)
        }

    var movieRating: String = ""
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieRating)
        }

    /** Section: Simple Properties. */

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    var movieBackdropSize: String = ""
    val movieGenresLiveData: MutableLiveData<List<GenreObservable>> = MutableLiveData()
    val movieKeywordsLiveData: MutableLiveData<List<KeywordObservable>> = MutableLiveData()
    val movieCreditsCastLiveData: MutableLiveData<List<CastObservable>> = MutableLiveData()
    val movieSimilarMoviesLiveData: MutableLiveData<List<SimilarMovieObservable>> = MutableLiveData()
    val movieVideosLiveData: MutableLiveData<List<VideoObservable>> = MutableLiveData()

    var movieIdToShow: Int? = null

    /** Section: Initialization. */

    fun initializeWithMovieId(id: Int) {
        // FIXME: Set it from settings repository.
        movieBackdropSize = "w780"
        scope.launch {
            loadMovieData(id)
        }.invokeOnCompletion {
            Log.d(this@MovieDetailsViewModel::class.java.simpleName, movieTitle)

        }
    }

    /** Section: Public Methods. */

    fun movieSimilarItemClicked(movieId: Int) {
        movieIdToShow = movieId
        action = ACTION_OPEN_MOVIE
    }

    /** Section: Private Methods. */

    private suspend fun loadMovieData(id: Int) {
        loading = true
        val resultDetails = movieRepository.getMovieDetails(id, LANGUAGE)

        // FIXME: Move language param to variable.
        val translationDetails =
            movieRepository.getMovieTranslations(id)?.translations?.firstOrNull { it.iso_639_1.equals(LANGUAGE, true) }
                ?.data

        val resultVideos = movieRepository.getMovieVideos(id, LANGUAGE)
        val resultKeywords = movieRepository.getMovieKeywords(id)
        val resultCredits = movieRepository.getMovieCredites(id)
        val resultSimilarMovies = movieRepository.getSimilarMovies(id, language = LANGUAGE)

        withContext(Dispatchers.Main) {
            movieTitle = translationDetails?.title ?: resultDetails?.title ?: ""
            movieTitleOriginal = resultDetails?.original_title ?: ""
            movieDescription = translationDetails?.overview ?: resultDetails?.overview ?: ""
            moviePosterPath = resultDetails?.poster_path ?: ""
            movieBackdropPath = resultDetails?.backdrop_path ?: ""
            movieOverallInfo = "${resultDetails?.production_countries?.map { it.name }?.joinToString(" / ")}"
            movieReleaseDate = resultDetails?.release_date ?: ""
            movieRuntime = resultDetails?.runtime ?: 0
            moviePopularity = resultDetails?.popularity ?: 0.0
            movieBudget = resultDetails?.budget ?: 0
            movieRevenue = resultDetails?.revenue ?: 0
            movieRating = resultDetails?.vote_average.toString()

            resultDetails?.genres?.let { getMovieGenres(it) }
            resultKeywords?.keywords?.let { getMovieKeywords(it) }
            resultCredits?.cast?.let { getMovieCastCredits(it) }
            resultSimilarMovies?.results?.let { getSimilarMovies(it) }
            resultVideos?.results?.let { getMovieVideos(it) }
        }
        Log.d(
            this@MovieDetailsViewModel::class.java.simpleName,
            "[$id]: ${translationDetails.toString()} \n ${movieGenresLiveData.value} \n $resultVideos ${movieVideosLiveData.value}"
        )

        loading = false
    }

    private fun getSimilarMovies(list: List<MovieEntity>) {
        movieSimilarMoviesEnabled = list.isNotEmpty()
        movieSimilarMoviesLiveData.postValue(
            list.take(MAX_SIMILAR_MOVIES)
                .map {
                    SimilarMovieObservable(it.id, it.title, it.backdrop_path ?: "", it.vote_average.toString())
                }
        )
    }

    private fun getMovieCastCredits(list: List<Cast>) {
        movieCreditsCastLiveData.postValue(
            list.filter {
                it.order <= MAX_CAST_ORDER
            }
                .map {
                    CastObservable(it.name, it.credit_id, it.cast_id, it.profile_path ?: "")
                }
        )
    }

    private fun getMovieVideos(list: List<MovieVideo>) {
        movieVideoEnabled = list.isNotEmpty()
        movieVideosLiveData.postValue(
            list.map {
                VideoObservable(it.key, it.name, it.site, it.size, it.type)
            }
        )
    }

    private fun getMovieGenres(responseGenres: List<MovieGenre>) {
        movieGenresLiveData.postValue(
            responseGenres.map {
                GenreObservable(it.id ?: 0, it.name ?: "")
            }
        )
    }

    private fun getMovieKeywords(list: List<Keyword>) {
        movieKeywordsLiveData.postValue(
            list.map {
                KeywordObservable(it.name, it.id)
            }
        )
    }

    companion object {
        const val ACTION_OPEN_MOVIE = 0
    }
}