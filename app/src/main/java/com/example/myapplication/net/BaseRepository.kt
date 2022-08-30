package com.example.myapplication.net

import androidx.viewbinding.BuildConfig

open class BaseRepository {

    /**
     *  所有调用API都需要通过这个方法进行代理
     *  因为需要解析返回的内容
     *  @param bolck: 是一个闭包函数，其中block就是执行闭包中的内容,这里suspend说明闭包是协程挂起
     *  通过runCatching来代替try、catch
     */
    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        runCatching {
            block()
        }.onSuccess { data: ApiResponse<T> ->
            return handleHttpOk(data)
        }.onFailure { e ->
            return handleHttpError(e)
        }
        return ApiEmptyResponse()
    }

    /**
     * 非后台返回错误，捕获到的异常
     */
    private fun <T> handleHttpError(e: Throwable): ApiErrorResponse<T> {
        if (BuildConfig.DEBUG) e.printStackTrace()
        return ApiErrorResponse(e)
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
            ApiFailedResponse(data.result, data.msg)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        val data = response.rows
        return if (data == null || data is List<*> && (data as List<*>).isEmpty()) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(data)
        }
    }

}