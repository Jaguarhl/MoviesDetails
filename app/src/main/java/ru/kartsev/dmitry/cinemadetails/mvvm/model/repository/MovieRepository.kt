package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository

import com.squareup.moshi.Moshi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.storage.CacheStorage
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
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types.newParameterizedType
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import ru.kartsev.dmitry.cinemadetails.mvvm.model.database.tables.cache.CachedData
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MovieRepository(
    private val moviesApi: MoviesApi,
    private val movieDetailsStorage: MovieDetailsStorage,
    private val cacheStorage: CacheStorage,
    private val settingsRepository: TmdbSettingsRepository,
    private val moshi: Moshi,
    private val util: Util
) : BaseRepository() {

    suspend fun getPopularMovies(page: Int, language: String? = null): PopularMoviesEntity? {
        return safeApiCall(
            call = { moviesApi.getPopularMovieAsync(language, page).await() },
            errorMessage = "Error Fetching Popular Movies."
        )
    }

    suspend fun getNowPlayingMovie(
        page: Int,
        language: String? = null,
        lifeTime: Int = 1
    ): NowPlayingMoviesEntity? {
        val requestId = "nowPlayingMovies_${page}_$language"

        val adapter: JsonAdapter<NowPlayingMoviesEntity> =
            moshi.adapter(newParameterizedType(NowPlayingMoviesEntity::class.java))

        val cachedResponse = cacheStorage.getCachedResponse(requestId)
        return if (cachedResponse != null && !util.isExpired(cachedResponse.timeStamp!!, lifeTime)) {
            adapter.fromJson(cachedResponse.response!!)
        } else {
            val response = safeApiCall(
                call = { moviesApi.getNowPlayingMovieAsync(language, page).await() },
                errorMessage = "Error Fetching Now Playing Movies."
            )

            if (response == null && cachedResponse != null) adapter.fromJson(cachedResponse.response!!)

            cacheStorage.addToCache(
                CachedData(
                    requestId,
                    System.currentTimeMillis(),
                    adapter.toJson(response)
                )
            )

            response
        }
    }

    suspend fun getMovieDetails(movieId: Int, language: String? = null, lifeTime: Int = 6): MovieDetailsEntity? {
        val currentLanguage = settingsRepository.currentLanguage
        val data = movieDetailsStorage.loadMovieDetailsById(movieId)
        val response: MovieDetailsEntity?

        if (data == null || util.isExpired(data.timeStamp, lifeTime) || data.language.equals(currentLanguage, true).not()) {
            response = safeApiCall(
                call = { moviesApi.getMovieByIdAsync(movieId, language).await() },
                errorMessage = "Error Fetching Movie Details."
            )

            response?.let {
                movieDetailsStorage.saveMovieDetails(
                    MovieDetailsEntity.getDetailsData(
                        it,
                        language,
                        System.currentTimeMillis()
                    )
                )
            }
        } else {
            response = MovieDetailsEntity.getDetailsEntityFromData(
                data,
                settingsRepository.genresList,
                settingsRepository.languagesList
            )
        }

        return response
    }

    suspend fun getMovieDetailsList(
        movieIds: List<Int>,
        language: String? = null
    ): List<MovieDetailsEntity>? {
        val data = movieDetailsStorage.loadMovieDetailsByList(movieIds)

        val response: List<MovieDetailsEntity>? = data?.mapNotNull {
            MovieDetailsEntity.getDetailsEntityFromData(
                it,
                settingsRepository.genresList,
                settingsRepository.languagesList
            )
        }

        if (data != null && data.size == movieIds.size) return response

        val cachedMovieIds: List<Int> = data?.map { it.id!! } ?: listOf()
        movieIds.filterNot { cachedMovieIds.contains(it) }.forEach {
            response?.toMutableList()?.add(getMovieDetails(it, language)!!)
        }

        return response
    }

    suspend fun getMovieAlternativeTitles(
        movieId: Int,
        country: String? = null
    ): MovieAlternativeTitlesEntity? {
        return safeApiCall(
            call = { moviesApi.getMovieAlternativeTitlesAsync(movieId, country).await() },
            errorMessage = "Error Fetching Movie Alternative Titles."
        )
    }

    suspend fun getMovieTranslations(
        movieId: Int,
        lifeTime: Int = TimeUnit.DAYS.toHours(7).toInt()
    ): MovieTranslationsEntity? {
        val requestId = "movieTranslations_$movieId"

        val adapter: JsonAdapter<MovieTranslationsEntity> =
            moshi.adapter(newParameterizedType(MovieTranslationsEntity::class.java))

        val cachedResponse = cacheStorage.getCachedResponse(requestId)
        return if (cachedResponse != null && !util.isExpired(cachedResponse.timeStamp!!, lifeTime)) {
            adapter.fromJson(cachedResponse.response!!)
        } else {
            val response = safeApiCall(
                call = { moviesApi.getMovieTranslationsAsync(movieId).await() },
                errorMessage = "Error Fetching Movie Translations."
            )

            if (response == null && cachedResponse != null) adapter.fromJson(cachedResponse.response!!)

            cacheStorage.addToCache(
                CachedData(
                    requestId,
                    System.currentTimeMillis(),
                    adapter.toJson(response)
                )
            )

            response
        }
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

    suspend fun getMovieKeywords(movieId: Int, lifeTime: Int = 24): MovieKeywordsEntity? {
        val requestId = "movieKeywords_$movieId"

        val adapter: JsonAdapter<MovieKeywordsEntity> =
            moshi.adapter(newParameterizedType(MovieKeywordsEntity::class.java))

        val cachedResponse = cacheStorage.getCachedResponse(requestId)

        return if (cachedResponse != null && !util.isExpired(cachedResponse.timeStamp!!, lifeTime)) {
            adapter.fromJson(cachedResponse.response!!)
        } else {
            val response = safeApiCall(
                call = { moviesApi.getMovieKeywordsAsync(movieId).await() },
                errorMessage = "Error Fetching Movie Keywords."
            )

            if (response == null && cachedResponse != null) adapter.fromJson(cachedResponse.response!!)

            cacheStorage.addToCache(
                CachedData(
                    requestId,
                    System.currentTimeMillis(),
                    adapter.toJson(response)
                )
            )

            response
        }
    }

    suspend fun getMovieCredits(movieId: Int, lifeTime: Int = TimeUnit.DAYS.toHours(31).toInt()): MovieCreditsEntity? {
        val requestId = "movieCredits_$movieId"

        val adapter: JsonAdapter<MovieCreditsEntity> =
            moshi.adapter(newParameterizedType(MovieCreditsEntity::class.java))

        val cachedResponse = cacheStorage.getCachedResponse(requestId)

        return if (cachedResponse != null && !util.isExpired(cachedResponse.timeStamp!!, lifeTime)) {
            adapter.fromJson(cachedResponse.response!!)
        } else {
            val response = safeApiCall(
                call = { moviesApi.getMovieCreditsAsync(movieId).await() },
                errorMessage = "Error Fetching Movie Credits."
            )

            if (response == null && cachedResponse != null) adapter.fromJson(cachedResponse.response!!)

            cacheStorage.addToCache(
                CachedData(
                    requestId,
                    System.currentTimeMillis(),
                    adapter.toJson(response)
                )
            )

            response
        }
    }

    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int? = null,
        language: String? = null,
        lifeTime: Int = 7
    ): SimilarMoviesEntity? {
        val requestId = "movieSimilar_${movieId}_${page}_$language"

        val adapter: JsonAdapter<SimilarMoviesEntity> =
            moshi.adapter(newParameterizedType(SimilarMoviesEntity::class.java))

        val cachedResponse = cacheStorage.getCachedResponse(requestId)

        return if (cachedResponse != null && !util.isExpired(cachedResponse.timeStamp!!, lifeTime)) {
            adapter.fromJson(cachedResponse.response!!)
        } else {
            val response = safeApiCall(
                call = { moviesApi.getSimilarMoviesAsync(movieId, language, page).await() },
                errorMessage = "Error Fetching Similar Movies."
            )

            if (response == null && cachedResponse != null) adapter.fromJson(cachedResponse.response!!)

            cacheStorage.addToCache(
                CachedData(
                    requestId,
                    System.currentTimeMillis(),
                    adapter.toJson(response)
                )
            )

            response
        }
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
        includeLanguages: String? = null,
        lifeTime: Int = 12
    ): MovieImagesEntity? {
        val requestId = "movieImages_${movieId}_${language}_$includeLanguages"

        val adapter: JsonAdapter<MovieImagesEntity> =
            moshi.adapter(newParameterizedType(MovieImagesEntity::class.java))

        val cachedResponse = cacheStorage.getCachedResponse(requestId)

        return if (cachedResponse != null && !util.isExpired(cachedResponse.timeStamp!!, lifeTime)) {
            adapter.fromJson(cachedResponse.response!!)
        } else {
            val response = safeApiCall(
                call = {
                    moviesApi.getMovieImagesAsync(movieId, language, includeLanguages.toString())
                        .await()
                },
                errorMessage = "Error Fetching Movie Images."
            )

            if (response == null && cachedResponse != null) adapter.fromJson(cachedResponse.response!!)

            cacheStorage.addToCache(
                CachedData(
                    requestId,
                    System.currentTimeMillis(),
                    adapter.toJson(response)
                )
            )

            response
        }
    }
}