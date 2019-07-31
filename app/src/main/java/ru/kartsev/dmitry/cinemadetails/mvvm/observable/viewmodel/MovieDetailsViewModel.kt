package ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.kartsev.dmitry.cinemadetails.BR
import ru.kartsev.dmitry.cinemadetails.common.config.AppConfig.MAX_CAST_ORDER
import ru.kartsev.dmitry.cinemadetails.common.helper.ObservableViewModel
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieVideoData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.credits.Cast
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.dates.Result
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieGenre
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieTranslationsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.keywords.Keyword
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.MovieEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.MovieRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.CastObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.GenreObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.KeywordObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.SimilarMovieObservable
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.baseobservable.VideoObservable
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class MovieDetailsViewModel : ObservableViewModel(), KoinComponent {
    /** Section: Injections. */

    private val movieRepository: MovieRepository by inject()
    private val settingsRepository: TmdbSettingsRepository by inject()
    private val util: Util by inject()

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

    var movieVideosCount: Int? = null
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieVideosCount)
        }

    var movieSimilarMoviesEnabled: Boolean = false
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieSimilarMoviesEnabled)
        }

    var movieSimilarMoviesCount: Int? = null
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieSimilarMoviesCount)
        }

    var movieKeywordsEnabled: Boolean = false
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieKeywordsEnabled)
        }

    var movieCreditsCastEnabled: Boolean = false
        @Bindable get() = field
        set(value) {
            field = if (field == value) return else value
            notifyPropertyChanged(BR.movieCreditsCastEnabled)
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
    private var language: String? = settingsRepository.currentLanguage

    var movieBackdropSize: String? = null
    var movieSimilarMovieBackdropSize: String? = null
    var moviePosterSize: String? = null
    val movieGenresLiveData: MutableLiveData<List<GenreObservable>> = MutableLiveData()
    val movieKeywordsLiveData: MutableLiveData<List<KeywordObservable>> = MutableLiveData()
    val movieCreditsCastLiveData: MutableLiveData<List<CastObservable>> = MutableLiveData()
    val movieSimilarMoviesLiveData: MutableLiveData<List<SimilarMovieObservable>> = MutableLiveData()
    val movieVideosLiveData: MutableLiveData<List<VideoObservable>> = MutableLiveData()

    var movieIdToShow: Int? = null

    /** Section: Initialization. */

    fun initializeWithMovieId(id: Int) {
        with(settingsRepository) {
            movieBackdropSize = backdropSizes[1]
            movieSimilarMovieBackdropSize = backdropSizes[0]
            moviePosterSize = posterSizes[0]
        }

        loadMovieData(id)
    }

    /** Section: Public Methods. */

    fun movieSimilarItemClicked(movieId: Int) {
        movieIdToShow = movieId
        action = ACTION_OPEN_MOVIE
    }

    fun getMovieInfoToShare(title: String, releaseDateLabel: String, genresLabel: String): String {
        var result = StringBuilder(title)
        if (movieReleaseDate.isNotEmpty()) result.append(
            "\n\n$releaseDateLabel $movieReleaseDate"
        )

        movieGenresLiveData.value?.let { list ->
            result.append(
                "\n\n$genresLabel ${list.toList().joinToString(
                    ", "
                ) { it.name }}"
            )
        }

        if (movieDescription.isNotEmpty()) result.append(
            "\n\n$movieDescription"
        )

        movieVideosLiveData.value?.let { list ->
            result.append(
                "\n\n${list.toList().joinToString(
                    "\n"
                ) { "https://youtu.be/${it.key}" }}"
            )
        }

        return result.toString()
    }

    /** Section: Private Methods. */

    private fun loadMovieData(id: Int) = try {
        scope.launch {
            loading = true
            val gettingResultsJob = async { movieRepository.getMovieDetails(id, language) }
            val gettingTranslationsJob = async { movieRepository.getMovieTranslations(id) }
            val gettingVideosJob = async { movieRepository.getMovieVideos(id, language) }
            val gettingKeywordsJob = async { movieRepository.getMovieKeywords(id) }
            val gettingCreditsJob = async { movieRepository.getMovieCredits(id) }
            val gettingImagesJob = async { movieRepository.getMovieImages(id, language) }
            val gettingSimilarMoviesJob = async { movieRepository.getSimilarMovies(id, language = language) }

            withContext(Dispatchers.Main) {
                displayDetails(gettingResultsJob.await(), gettingTranslationsJob.await())
                gettingKeywordsJob.await()?.keywords?.let { getMovieKeywords(it) }
                gettingCreditsJob.await()?.cast?.let { getMovieCastCredits(it) }
                gettingSimilarMoviesJob.await()?.results?.let { getSimilarMovies(it) }
                gettingVideosJob.await()?.let { getMovieVideos(it) }
            }
//        val resultReleaseDates = movieRepository.getMovieReleaseDates(id)

//            Timber.d(
//                "[$id]: ${translationDetails.toString()} \n $resultDetails \n ${movieGenresLiveData.value} \n $resultVideos ${movieVideosLiveData.value}"
//            )
        }.invokeOnCompletion {
            Timber.d("$movieTitle, backdropSize: $movieBackdropSize, similarMovieBackdropSize: $movieSimilarMovieBackdropSize")

        }
    } catch (exception: Exception) {
        Timber.w(exception)
    }

    private fun displayDetails(resultDetails: MovieDetailsEntity?, translationDetails: MovieTranslationsEntity?) {
        val translated = translationDetails?.translations?.firstOrNull {
            it.iso_639_1.equals(
                language,
                true
            )
        }
            ?.data

        movieTitle = getMovieTitle(translated?.title, resultDetails?.title)
        movieTitleOriginal = resultDetails?.original_title ?: ""
        movieDescription = translated?.overview ?: resultDetails?.overview ?: ""
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

        if (movieBackdropPath.isEmpty()) action = ACTION_COLLAPSE_TOOLBAR

        loading = false
    }

    private fun getMovieTitle(translatedTitle: String?, originalTitle: String?): String =
        if (!translatedTitle.isNullOrEmpty()) {
            translatedTitle
        } else if (!originalTitle.isNullOrEmpty()) {
            originalTitle
        } else ""

    private fun getReleaseDates(list: List<Result>): List<String> =
        list.first { it.iso_3166_1.equals(settingsRepository.currentLanguage, true) }.release_dates.map {
            "$settingsRepository.currentLanguage: ${if (it.note.isNotEmpty()) {
                "${it.note}:"
            } else ""} ${util.formatTime(it.release_date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd")}"
        }

    private fun getSimilarMovies(list: List<MovieEntity>) {
        movieSimilarMoviesEnabled = list.isNotEmpty()
        movieSimilarMoviesCount = /*if (list.size > MAX_SIMILAR_MOVIES) { MAX_SIMILAR_MOVIES } else */list.size
        movieSimilarMoviesLiveData.postValue(
            list/*.take(MAX_SIMILAR_MOVIES)*/
                .map {
                    SimilarMovieObservable(it.id, it.title, it.backdrop_path ?: "", it.vote_average.toString())
                }
        )
    }

    private fun getMovieCastCredits(list: List<Cast>) {
        movieCreditsCastEnabled = list.isNotEmpty()
        movieCreditsCastLiveData.postValue(
            list.filter {
                it.order <= MAX_CAST_ORDER
            }
                .map {
                    CastObservable(it.name, it.credit_id, it.cast_id, it.profile_path ?: "")
                }
        )
    }

    private fun getMovieVideos(list: List<MovieVideoData>) {
        movieVideoEnabled = list.isNotEmpty()
        movieVideosCount = list.size
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
        movieKeywordsEnabled = list.isNotEmpty()
        movieKeywordsLiveData.postValue(
            list.map {
                KeywordObservable(it.name, it.id)
            }
        )
    }

    companion object {
        const val ACTION_OPEN_MOVIE = 0
        const val ACTION_COLLAPSE_TOOLBAR = 1
    }
}