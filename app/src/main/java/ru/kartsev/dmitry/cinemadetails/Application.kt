package ru.kartsev.dmitry.cinemadetails

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger
import org.koin.log.EmptyLogger
import ru.kartsev.dmitry.cinemadetails.common.di.ContextModule
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule
import ru.kartsev.dmitry.cinemadetails.common.di.StorageModule
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Initialize Koin DI modules.
        startKoin(
            this,
            listOf(ContextModule.it, NetworkModule.it, RepositoryModule.it, StorageModule.it),
            logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger()
        )
    }
}