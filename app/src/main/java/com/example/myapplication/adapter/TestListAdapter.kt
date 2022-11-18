package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.base.BaseDataBindingAdapter
import com.example.myapplication.databinding.ItemTestBinding
import com.example.myapplication.net.model.TestResponseModel
import com.example.myapplication.util.diff.TestDiff

/**
 * 商品列表Adapter
 */
class TestListAdapter(val context: Context?) :
    BaseDataBindingAdapter<TestResponseModel, ItemTestBinding>(
        context, TestDiff.getShopItemCallback()
    ) {
    override fun onBindItem(
        binding: ViewDataBinding?,
        item: TestResponseModel?,
        holder: RecyclerView.ViewHolder?
    ) {
        if (binding is ItemTestBinding) {
            // 绘制view
        }
    }

    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_test
    }
}