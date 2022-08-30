package com.example.myapplication.vm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.net.model.TestResponseModel

class TestViewModel : BaseViewModel() {

    // view层需要监听的数据
    var mTestList: MutableLiveData<TestResponseModel> = MutableLiveData()

    override fun initViewModel(lifecycleOwner: LifecycleOwner) {
        mLifecycleOwner = lifecycleOwner
    }

    suspend fun queryCollectSceneList(id: Int, name: String) {
        handleResponse(repository.queryCollectSceneList(id, name)) {
            it.onSuccess {
                mTestList.value = it
            }
            it.onEmpty {
            }
            it.onComplete {
            }
            it.onFailed { s, s2 ->
            }
        }
    }

}