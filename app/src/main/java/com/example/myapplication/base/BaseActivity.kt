package com.example.myapplication.base

import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.util.DataBindingConfig
import com.example.myapplication.util.ActivityManager

abstract class BaseActivity : AppCompatActivity() {

    private var mBinding: ViewDataBinding? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null
    protected abstract fun initViewModel()

    protected abstract fun getDataBindingConfig(): DataBindingConfig?

    abstract fun initView()

    abstract fun initData()


    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     */
    protected fun getBinding(): ViewDataBinding {
        return mBinding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化viewModel
        initViewModel()
        // 初始化viewBinding
        initVieBinding()
        initView()
        initData()
        ActivityManager.pushActivity(this)
    }

    private fun initVieBinding() {
        // 获取dataBinding需要params参数
        val dataBindingConfig = getDataBindingConfig()
        // 进行数据绑定
        val binding =
            DataBindingUtil.setContentView<ViewDataBinding>(this, dataBindingConfig!!.layout)
        binding.lifecycleOwner = this
        // 设置viewModel
        binding.setVariable(dataBindingConfig.vmVariableId, dataBindingConfig.stateViewModel)
        // 获取activity中的params对象
        val bindingParams = dataBindingConfig.getBindingParams()
        // 把全部params设置到viewBinding中
        bindingParams.forEach { key, value ->
            binding.setVariable(key, value)
        }
        mBinding = binding
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(Bundle())
    }

    open fun isDebug(): Boolean {
        return applicationContext.applicationInfo != null &&
                applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }

    /**
     *  viewModel存储到当前activity
     */
    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider!![modelClass]
    }

    /**
     *  viewModel存储到当前Application
     */
    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider((this.applicationContext as App))
        }
        return mApplicationProvider!![modelClass]
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding!!.unbind()
        mBinding = null
    }
}