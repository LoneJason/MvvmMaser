package com.example.myapplication.util.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.net.model.TestResponseModel

/**
 * 判断返回数据是否相同，如果相同会刷新view，如果不同就会刷新
 */
object TestDiff {
    private var mShopItemCallback: DiffUtil.ItemCallback<TestResponseModel>? = null
    fun getShopItemCallback(): DiffUtil.ItemCallback<TestResponseModel> {
        if (mShopItemCallback == null) {
            mShopItemCallback = object : DiffUtil.ItemCallback<TestResponseModel>() {
                override fun areItemsTheSame(
                    oldItem: TestResponseModel,
                    newItem: TestResponseModel
                ): Boolean {
                    return oldItem == newItem
                }
                override fun areContentsTheSame(
                    oldItem: TestResponseModel,
                    newItem: TestResponseModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
        return mShopItemCallback!!
    }
}