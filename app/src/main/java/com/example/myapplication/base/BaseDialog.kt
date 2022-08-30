package com.example.myapplication.base

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.util.DataBindingConfig
import com.example.myapplication.util.DisplayUtil

abstract class BaseDialog : DialogFragment() {
    protected var mActivity: AppCompatActivity? = null
    private var mDialogProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    protected abstract fun initViewModel()

    protected abstract fun getDataBindingConfig(): DataBindingConfig

    protected abstract fun initView(view: View?)

    protected abstract fun disPlayWidth(): Int

    protected abstract fun disPlayHeight(): Int

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
        val dataBindingConfig = getDataBindingConfig()
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, dataBindingConfig.layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(dataBindingConfig.vmVariableId, dataBindingConfig.stateViewModel)
        val bindingParams = dataBindingConfig.getBindingParams()
        // 把全部params设置到viewBinding中
        bindingParams.forEach { key, value ->
            binding.setVariable(key, value)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        // 因为fragment的container是空的，它的大小需要动态设置
        dialog?.window?.setLayout(
            DisplayUtil.dp2px(context, disPlayWidth()), DisplayUtil.dp2px(context, disPlayHeight())
        )
    }

    protected fun <T : ViewModel> getDialogScopeViewModel(modelClass: Class<T>): T {
        if (mDialogProvider == null) {
            mDialogProvider = ViewModelProvider(this)
        }
        return mDialogProvider!![modelClass]
    }

    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(mActivity!!)
        }
        return mActivityProvider!![modelClass]
    }

    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider =
                ViewModelProvider((mActivity?.applicationContext as App))
        }
        return mApplicationProvider!![modelClass]
    }


}