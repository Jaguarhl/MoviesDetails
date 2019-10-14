package ru.kartsev.dmitry.cinemadetails.common.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.fragments.HistoryFragment
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs.NowPlayingFragment
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs.StartFragment
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs.WatchlistFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeStartFragment(): StartFragment

    @ContributesAndroidInjector
    abstract fun contributeHistoryFragment(): HistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeNowPlayingFragment(): NowPlayingFragment

    @ContributesAndroidInjector
    abstract fun contributeWatchlistFragment(): WatchlistFragment
}