package ru.kartsev.dmitry.cinemadetails.common.di

import android.content.Context
import android.net.Uri
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Cache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.kartsev.dmitry.cinemadetails.BuildConfig
import ru.kartsev.dmitry.cinemadetails.common.config.AppConfig.TMDB_API_KEY
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.BASE_URL
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.CACHE_SIZE
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.MoviesApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.SettingsApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.TmdbSettingsRepository

object NetworkModule {
    private const val CACHE_NAME = "network.cache"
    const val MOSHI_NAME = "network.moshi"
    private const val HTTP_RETROFIT_NAME = "network.retrofit"
    private const val HTTP_CLIENT_RETROFIT_NAME = "network.http_client_retrofit"
    private const val HTTP_AUTH_INTERCEPTOR_NAME = "network.http_client_interceptor_retrofit"
    const val API_MOVIES = "network.api_movies"
    const val API_SETTINGS = "network.api_settings"
    const val PICASSO_NAME = "network.picasso"
    private const val PICASSO_CLIENT_NAME = "network.picasso_client"
    private const val PICASSO_INTERCEPTOR_NAME = "network.picasso_client_interceptor"
    private const val PICASSO_REQUEST_TRANSFORMER_NAME = "network.picasso_request_transformer"

    val it: Module = module {
        single(named(CACHE_NAME)) {
            Cache(get<Context>().cacheDir, CACHE_SIZE)
        }

        single(named(HTTP_AUTH_INTERCEPTOR_NAME)) {
            Interceptor {
                val newUrl = it.request().url().newBuilder().
                    addQueryParameter("api_key", TMDB_API_KEY).
                    build()

                val newRequest = it.request().newBuilder()
                    .url(newUrl)
                    .build()

                it.proceed(newRequest)
            }
        }

        single<OkHttpClient>(named(HTTP_CLIENT_RETROFIT_NAME)) {
            OkHttpClient.Builder()
                .addInterceptor(get(named(HTTP_AUTH_INTERCEPTOR_NAME)))
                .cache(get(named(CACHE_NAME)))
                .build()
        }

        single<Retrofit>(named(HTTP_RETROFIT_NAME)) {
            Retrofit.Builder().client(get(named(HTTP_CLIENT_RETROFIT_NAME)))
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        single(named(API_MOVIES)) {
            get<Retrofit>(named(HTTP_RETROFIT_NAME)).create(MoviesApi::class.java)
        }

        single(named(API_SETTINGS)) {
            get<Retrofit>(named(HTTP_RETROFIT_NAME)).create(SettingsApi::class.java)
        }

        single<Picasso>(named(PICASSO_NAME)) {
            Picasso.Builder(get())
                .downloader(OkHttp3Downloader(get<OkHttpClient>(OkHttpClient::class.java, named(PICASSO_CLIENT_NAME))))
                .requestTransformer(get(Picasso.RequestTransformer::class.java, named(PICASSO_REQUEST_TRANSFORMER_NAME)))
                .loggingEnabled(BuildConfig.DEBUG)
                .build()
        }

        single<OkHttpClient>(named(PICASSO_CLIENT_NAME)) {
            OkHttpClient.Builder()
                .addInterceptor(get(named(PICASSO_INTERCEPTOR_NAME)))
                .cache(get(named(CACHE_NAME)))
                .build()
        }

        single(named(PICASSO_INTERCEPTOR_NAME)) {
            Interceptor {
                val newUrl = it.request().url().newBuilder().
                    addQueryParameter("api_key", TMDB_API_KEY).
                    build()

                val newRequest = it.request().newBuilder()
                    .url(newUrl)
                    .build()

                it.proceed(newRequest)
            }
        }

        single(named(PICASSO_REQUEST_TRANSFORMER_NAME)) {
            Picasso.RequestTransformer { request -> Request.Builder(Uri.parse("${get<TmdbSettingsRepository>(named(
                RepositoryModule.TMDB_SETTINGS_REPOSITORY_NAME
            )).imagesBaseUrl}${request.uri}")).build() }
        }

        single(named(MOSHI_NAME)) {
            Moshi.Builder().build()
        }
    }
}