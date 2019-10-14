package ru.kartsev.dmitry.cinemadetails

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import ru.kartsev.dmitry.cinemadetails.common.di.AppInjector
import ru.kartsev.dmitry.cinemadetails.common.di.ViewModelModule
import ru.kartsev.dmitry.cinemadetails.common.di.ContextModule
import ru.kartsev.dmitry.cinemadetails.common.di.ConverterModule
import ru.kartsev.dmitry.cinemadetails.common.di.NetworkModule
import ru.kartsev.dmitry.cinemadetails.common.di.RepositoryModule
import ru.kartsev.dmitry.cinemadetails.common.di.StorageModule
import timber.log.Timber
import javax.inject.Inject

class Application : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppInjector.init(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}