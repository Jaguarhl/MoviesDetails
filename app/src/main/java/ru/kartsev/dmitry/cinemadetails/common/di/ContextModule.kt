package ru.kartsev.dmitry.cinemadetails.common.di

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import ru.kartsev.dmitry.cinemadetails.common.utils.Util

object ContextModule {
    /** Section: Constants. */
    const val UTIL_NAME = "context.util"

    /** Section: Modules. */

    val it: Module = module {
        single(UTIL_NAME) {
            Util()
        }
    }
}