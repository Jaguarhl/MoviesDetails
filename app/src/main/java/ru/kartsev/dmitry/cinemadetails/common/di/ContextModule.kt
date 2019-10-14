package ru.kartsev.dmitry.cinemadetails.common.di

import dagger.Module
import dagger.Provides
import ru.kartsev.dmitry.cinemadetails.common.utils.Util
import javax.inject.Singleton

@Module
class ContextModule {
    @Provides
    @Singleton
    fun provideUtil() = Util()
}