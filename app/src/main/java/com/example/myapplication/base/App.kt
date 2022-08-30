package com.example.myapplication.base

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class App : Application(), ViewModelStoreOwner {

    companion object {
        private lateinit var instance: Application

        @JvmStatic
        fun getInstance(): Application {
            return instance
        }
    }

    private lateinit var mAppViewModelStore: ViewModelStore

    override fun onCreate() {
        super.onCreate()
        instance = this
        mAppViewModelStore = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    /**
     * application 初始化工作
     */
    fun appInit() {

    }
}