package ru.kartsev.dmitry.cinemadetails.common.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.HistoryViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieDetailsViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieImageViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.NowPlayingViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.WatchlistViewModel

object ViewModelModule {
    val it: Module = module {
        viewModel { MovieDetailsViewModel(get(), get(), get(), get()) }
        viewModel { MovieImageViewModel(get()) }
        viewModel { NowPlayingViewModel(get(), get()) }
        viewModel { WatchlistViewModel(get(), get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}