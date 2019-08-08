package ru.kartsev.dmitry.cinemadetails

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger
import ru.kartsev.dmitry.cinemadetails.common.di.ContextModule
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule
import ru.kartsev.dmitry.cinemadetails.common.di.StorageModule
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin DI modules.
        startKoin{
            AndroidLogger()
            androidContext(this@Application)
            modules(listOf(ContextModule.it, NetworkModule.it, RepositoryModule.it, StorageModule.it))
        }

        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}