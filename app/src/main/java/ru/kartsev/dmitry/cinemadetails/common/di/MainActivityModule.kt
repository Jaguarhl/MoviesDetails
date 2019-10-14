package ru.kartsev.dmitry.cinemadetails.common.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}