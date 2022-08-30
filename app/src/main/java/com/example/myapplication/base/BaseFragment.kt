package com.example.myapplication.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.util.DataBindingConfig

abstract class BaseFragment : Fragment() {

    protected var mActivity: AppCompatActivity? = null
    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    protected abstract fun initViewModel()

    protected abstract fun getDataBindingConfig(): DataBindingConfig?

    abstract fun initView()

    abstract fun initData()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 获取dataBinding需要params参数
        val dataBindingConfig = getDataBindingConfig()
        // 进行数据绑定
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, dataBindingConfig?.getLayout()!!, container, false)
        binding.lifecycleOwner = this
        // 设置viewModel
        binding.setVariable(dataBindingConfig.vmVariableId, dataBindingConfig.stateViewModel)
        // 获取fragment中的params对象
        val bindingParams = dataBindingConfig.getBindingParams()
        // 把全部params设置到viewBinding中
        bindingParams.forEach { key, value ->
            binding.setVariable(key, value)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    /**
     *  viewModel存储到当前fragment
     */
    protected fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!![modelClass]
    }

    /**
     *  viewModel存储到当前activity
     */
    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(mActivity!!)
        }
        return mActivityProvider!![modelClass]
    }

    /**
     *  viewModel存储到当前application
     */
    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider =
                ViewModelProvider((mActivity?.applicationContext as App))
        }
        return mApplicationProvider!![modelClass]
    }

    protected val applicationContext: Context
        protected get() = mActivity?.applicationContext!!
}