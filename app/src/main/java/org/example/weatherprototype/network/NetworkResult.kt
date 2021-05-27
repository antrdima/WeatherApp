package org.example.weatherprototype.network

sealed class NetworkResult<out R> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class NoConnection(val exception: Exception) : NetworkResult<Nothing>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is NoConnection -> "NoConnection[exception=$exception]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

/**
 * `true` if [NetworkResult] is of type [Success] & holds non-null [Success.data].
 */
val NetworkResult<*>.succeeded
    get() = this is NetworkResult.Success && data != null
