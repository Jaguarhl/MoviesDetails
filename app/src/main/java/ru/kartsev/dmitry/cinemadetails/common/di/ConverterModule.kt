package ru.kartsev.dmitry.cinemadetails.common.di

import dagger.Provides
import ru.kartsev.dmitry.cinemadetails.mvvm.model.converter.MovieItemConverter
import javax.inject.Singleton

@dagger.Module
class ConverterModule {
    @Provides
    @Singleton
    fun provideMovieItemConverter() = MovieItemConverter()
}