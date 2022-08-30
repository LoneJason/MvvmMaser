package com.example.myapplication.net

import com.example.myapplication.net.model.TestRequestModel
import com.example.myapplication.net.model.TestResponseModel
import java.util.HashMap

/**
 *  这里你可以理解为MVVM的M层，主要就是数据获取
 *  M层其实就是数据获取源，比如数据库、远程服务器、本地文件等
 */
class ProjectRepository : BaseRepository() {
    private val mService by lazy {
        RetrofitClient.instance.getService(ApiService::class.java)
    }

    suspend fun queryCollectSceneList(id: Int, name: String): ApiResponse<TestResponseModel> {
        return executeHttp {
            val requestModel = TestRequestModel(id, name)
            // 设置请求头
            val headMap: MutableMap<String, String> = HashMap()
            headMap.put("head", "xxxxxx")
            // 开始调用retrofit请求
            mService.queryCollectSceneList(headMap, requestModel)
        }
        // 下面这段是模拟网络response
        // return ApiResponse(TestResponseModel("aaa","bbbb"),"1","aaaa")

    }
}