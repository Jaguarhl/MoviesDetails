package ru.kartsev.dmitry.cinemadetails.common.di

import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

object CommonModule {
    private const val COMPOSITE_DISPOSABLE_NAME = "composite.disposable"

    val it : Module = module {
        single(COMPOSITE_DISPOSABLE_NAME) {
            CompositeDisposable()
        }
    }
}