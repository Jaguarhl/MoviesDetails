package ru.kartsev.dmitry.cinemadetails

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger
import org.koin.log.EmptyLogger
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin DI modules.
        startKoin(
            this,
            listOf(NetworkModule.it, RepositoryModule.it),
            logger = if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger()
        )
    }
}