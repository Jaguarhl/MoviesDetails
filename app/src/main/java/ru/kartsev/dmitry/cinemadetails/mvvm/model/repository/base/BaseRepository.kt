package ru.kartsev.dmitry.cinemadetails.mvvm.model.repository.base

import org.koin.core.KoinComponent
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

open class BaseRepository : KoinComponent {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result : Result<T> = safeApiResult(call,errorMessage)
        var data : T? = null

        when(result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                Timber.d("1.DataRepository: $errorMessage & Exception - ${result.exception}")
            }
        }

        return data
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String) : Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) return Result.Success(
                response.body()!!
            )

            return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
        } catch (exception: HttpException) {
            Timber.w(exception, "Exception on HTTP request.")
            return Result.Error(exception)
        }
    }
}

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}