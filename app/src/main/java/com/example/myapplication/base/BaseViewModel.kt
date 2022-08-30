package com.example.myapplication.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.myapplication.net.*

abstract class BaseViewModel : ViewModel() {

    // 持有网络数据请求对象
    protected val repository by lazy { ProjectRepository() }
    protected lateinit var mLifecycleOwner: LifecycleOwner
    abstract fun initViewModel(mLifecycleOwner: LifecycleOwner)

    /**
     *  对网络请求response进行数据分析
     */
    protected fun <T> handleResponse(
        response: ApiResponse<T>,
        listenerBuilder: (listener: ListenerBuilder<T>) -> Unit
    ) {
        // 创建一个ListenerBuilder，然后赋值给listenerBuilder闭包
        val listener = ListenerBuilder<T>().also(listenerBuilder)
        // 判断当前response类型，回调到外层
        when (response) {
            is ApiSuccessResponse -> listener.mSuccessListenerAction?.invoke(response.response)
            is ApiEmptyResponse -> listener.mEmptyListenerAction?.invoke()
            is ApiFailedResponse -> listener.mFailedListenerAction?.invoke(
                response.result,
                response.msg
            )
            is ApiErrorResponse -> listener.mErrorListenerAction?.invoke(response.throwable)
        }
        listener.mCompleteListenerAction?.invoke()
    }

    inner class ListenerBuilder<T> {
        internal var mSuccessListenerAction: ((T) -> Unit)? = null
        internal var mErrorListenerAction: ((Throwable) -> Unit)? = null
        internal var mEmptyListenerAction: (() -> Unit)? = null
        internal var mCompleteListenerAction: (() -> Unit)? = null
        internal var mFailedListenerAction: ((String?, String?) -> Unit)? = null

        fun onSuccess(action: (T) -> Unit) {
            mSuccessListenerAction = action
        }

        fun onFailed(action: (String?, String?) -> Unit) {
            mFailedListenerAction = action
        }

        fun onError(action: (Throwable) -> Unit) {
            mErrorListenerAction = action
        }

        fun onEmpty(action: () -> Unit) {
            mEmptyListenerAction = action
        }

        fun onComplete(action: () -> Unit) {
            mCompleteListenerAction = action
        }
    }

}