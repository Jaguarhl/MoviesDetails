package ru.kartsev.dmitry.cinemadetails.common.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.HistoryViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.details.MovieDetailsViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.observable.viewmodel.MovieImageViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs.NowPlayingViewModel
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs.WatchlistViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    internal abstract fun historyViewModel(viewModel: HistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    internal abstract fun movieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieImageViewModel::class)
    internal abstract fun movieImageViewModel(viewModel: MovieImageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NowPlayingViewModel::class)
    internal abstract fun nowPlayingViewModel(viewModel: NowPlayingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WatchlistViewModel::class)
    internal abstract fun watchlistViewModel(viewModel: WatchlistViewModel): ViewModel
}