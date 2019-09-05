package ru.kartsev.dmitry.cinemadetails.common.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.kartsev.dmitry.cinemadetails.common.utils.Util

object ContextModule {
    /** Section: Modules. */

    val it: Module = module {
        single { Util() }
    }
}