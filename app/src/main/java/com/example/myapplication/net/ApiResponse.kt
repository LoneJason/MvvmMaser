package com.example.myapplication.net

import java.io.Serializable

/**
 *  Api数据经过转换处理的数据格式
 */
open class ApiResponse<T>(
        open val rows: T? = null,
        open val result: String? = null,
        open val msg: String? = null,
        open val error: Throwable? = null,
) : Serializable {
    val isSuccess: Boolean
        get() = result == "1"
}

data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(rows = response)

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiFailedResponse<T>(override val result: String?, override val msg: String?) : ApiResponse<T>(result = result, msg = msg)

data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>(error = throwable)
