package ru.kartsev.dmitry.cinemadetails.common.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.kartsev.dmitry.cinemadetails.common.config.AppConfig.TMDB_API_KEY
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.BASE_URL
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.CACHE_SIZE
import ru.kartsev.dmitry.cinemadetails.common.config.NetworkConfig.CONNECTION_TIMEOUT
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.MoviesApi
import ru.kartsev.dmitry.cinemadetails.mvvm.model.network.api.SettingsApi
import java.util.concurrent.TimeUnit

object NetworkModule {
    private const val CACHE_NAME = "network.cache"
    private const val HTTP_RETROFIT_NAME = "network.retrofit"
    private const val HTTP_CLIENT_RETROFIT_NAME = "network.http_client_retrofit"
    private const val HTTP_AUTH_INTERCEPTOR_NAME = "network.http_client_interceptor_retrofit"
    const val API_MOVIES = "network.api_movies"
    const val API_SETTINGS = "network.api_settings"
    const val PICASSO_NAME = "network.picasso"

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
                .callTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }

        single<Retrofit>(named(HTTP_RETROFIT_NAME)) {
            Retrofit.Builder().client(get(named(HTTP_CLIENT_RETROFIT_NAME)))
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        single {
            get<Retrofit>(named(HTTP_RETROFIT_NAME)).create(MoviesApi::class.java)
        }

        single {
            get<Retrofit>(named(HTTP_RETROFIT_NAME)).create(SettingsApi::class.java)
        }

        single {
            Moshi.Builder().build()
        }
    }
}