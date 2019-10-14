package ru.kartsev.dmitry.cinemadetails.common.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.details.MovieDetailsFragment

@Suppress("unused")
@Module
abstract class MovieDetailsActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MovieDetailsFragment
}