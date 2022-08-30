package com.example.myapplication.view.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.util.DataBindingConfig
import com.example.myapplication.vm.TestViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : BaseFragment() {

    private var mViewModel: TestViewModel? = null
    override fun initViewModel() {
        // 跟activity保持一致的生命周期
        mViewModel = getActivityScopeViewModel(TestViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_second, BR.vm, mViewModel)
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun initView() {
    }

    override fun initData() {
    }

    inner class ClickProxy {
        fun btnClick() {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}