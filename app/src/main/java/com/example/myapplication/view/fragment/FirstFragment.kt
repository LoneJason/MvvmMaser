package com.example.myapplication.view.fragment

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.util.DataBindingConfig
import com.example.myapplication.vm.TestViewModel
import kotlinx.coroutines.launch


class FirstFragment : BaseFragment() {

    private var mViewModel: TestViewModel? = null
    override fun initViewModel() {
        // 跟activity保持一致的生命周期
        mViewModel = getActivityScopeViewModel(TestViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            R.layout.fragment_first,
            BR.vm,
            mViewModel
        )
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun initView() {
    }

    override fun initData() {
        mViewModel?.initViewModel(this)
        lifecycleScope.launch {
            mViewModel?.queryCollectSceneList(11, "aaa")
        }
    }

    inner class ClickProxy {
        fun btnClick() {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}