package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import org.koin.core.inject
import org.koin.core.qualifier.named
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule.API_MOVIES
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule.TMDB_SETTINGS_REPOSITORY_NAME
import ru.kartsev.dmitry.cinemadetails.common.di.StorageModule.MOVIE_DETAILS_STORAGE_NAME
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.MovieDetailsStorage
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.details.MovieVideoData
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.credits.MovieCreditsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.dates.ReleaseDatesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieAlternativeTitlesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieDetailsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.details.MovieTranslationsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.images.MovieImagesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.keywords.MovieKeywordsEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.now_playing.NowPlayingMoviesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.popular.PopularMoviesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.similar.SimilarMoviesEntity
import ru.kartsev.dmitry.cinemadetails.mvvm.model.entities.videos.MovieVideo
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.MoviesApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base.BaseRepository

class MovieRepository : BaseRepository() {
    /** Section: Injections. */

    private val moviesApi: MoviesApi by inject(named(API_MOVIES))
    private val movieDetailsStorage: MovieDetailsStorage by inject(named(MOVIE_DETAILS_STORAGE_NAME))
    private val settingsRepository: TmdbSettingsRepository by inject(named(TMDB_SETTINGS_REPOSITORY_NAME))

    suspend fun getPopularMovies(page: Int, language: String? = null): PopularMoviesEntity? {
        return safeApiCall(
            call = { moviesApi.getPopularMovieAsync(language, page).await() },
            errorMessage = "Error Fetching Popular Movies."
        )
    }

    suspend fun getNowPlayingMovie(page: Int, language: String? = null): NowPlayingMoviesEntity? {
        return safeApiCall(
            call = { moviesApi.getNowPlayingMovieAsync(language, page).await() },
            errorMessage = "Error Fetching Now Playing Movies."
        )
    }

    suspend fun getMovieDetails(movieId: Int, language: String? = null): MovieDetailsEntity? {
        val currentLanguage = settingsRepository.currentLanguage
        val data = movieDetailsStorage.loadMovieDetailsById(movieId)
        val response: MovieDetailsEntity?

        if (data == null || data.isExpired || data.language.equals(currentLanguage, true).not()) {
            response = safeApiCall(
                call = { moviesApi.getMovieByIdAsync(movieId, language).await() },
                errorMessage = "Error Fetching Movie Details."
            )

            response?.
                let {movieDetailsStorage.saveMovieDetails(MovieDetailsEntity.getDetailsData(it, language)) }
        } else {
            response = MovieDetailsEntity.getDetailsEntityFromData(data, settingsRepository.genresList, settingsRepository.languagesList)
        }

        return response
    }

    suspend fun getMovieAlternativeTitles(movieId: Int, country: String? = null): MovieAlternativeTitlesEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieAlternativeTitlesAsync(movieId, country).await() },
            errorMessage = "Error Fetching Movie Alternative Titles."
        )
    }

    suspend fun getMovieTranslations(movieId: Int): MovieTranslationsEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieTranslationsAsync(movieId).await() },
            errorMessage = "Error Fetching Movie Translations."
        )
    }

    suspend fun getMovieVideos(movieId: Int, language: String? = null): List<MovieVideoData>? {
        val currentLanguage = settingsRepository.currentLanguage!!
        val data = movieDetailsStorage.loadMovieVideosById(movieId, language ?: currentLanguage)

        val response: List<MovieVideoData>?

        if (data.isNullOrEmpty() || data[0].iso_639_1.equals(currentLanguage, true).not()) {
            response = safeApiCall(
                call = { moviesApi.getMovieVideosAsync(movieId, language).await() },
                errorMessage = "Error Fetching Movie Videos."
            )?.results?.map { MovieVideo.getMovieVideoData(movieId, it) }

            response?.let { movieDetailsStorage.saveMovieVideosList(it) }

        } else {
            response = data
        }

        return response
    }

    suspend fun getMovieKeywords(movieId: Int): MovieKeywordsEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieKeywordsAsync(movieId).await() },
            errorMessage = "Error Fetching Movie Keywords."
        )
    }

    suspend fun getMovieCredits(movieId: Int): MovieCreditsEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieCreditsAsync(movieId).await() },
            errorMessage = "Error Fetching Movie Credits."
        )
    }

    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int? = null,
        language: String? = null
    ): SimilarMoviesEntity? {
        return safeApiCall(
            call = { moviesApi.getSimilarMoviesAsync(movieId, language, page).await() },
            errorMessage = "Error Fetching Similar Movies."
        )
    }

    suspend fun getMovieReleaseDates(
        movieId: Int
    ): ReleaseDatesEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieReleaseDatesAsync(movieId).await() },
            errorMessage = "Error Fetching Movie Release Dates."
        )
    }

    suspend fun getMovieImages(
        movieId: Int,
        language: String? = null,
        includeLanguages: String? = null
    ): MovieImagesEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieImagesAsync(movieId, language, includeLanguages.toString()).await() },
            errorMessage = "Error Fetching Movie Images."
        )
    }
}