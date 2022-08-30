package com.example.myapplication.util

import android.app.Activity
import android.util.Log
import java.util.*

object ActivityManager {
    private val mActivityStack: Stack<Activity> = Stack()

    /**
     * 入栈
     */
    fun pushActivity(activity: Activity) {
        mActivityStack.add(activity)
    }

    /**
     * 退出某个activity
     */
    fun removeActivity(activity: Activity) {
        mActivityStack.remove(activity)
    }

    /**
     *  退出栈顶
     */
    fun popActivity() {
        val activity = mActivityStack.pop()
    }

    /**
     * 退出所有Activity
     */
    fun finishAllActivity() {
        for (activity in mActivityStack) {
            activity.finish()
        }
        mActivityStack.clear()
    }
}