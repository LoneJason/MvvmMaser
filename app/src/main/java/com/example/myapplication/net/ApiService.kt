package com.example.myapplication.net

import com.example.myapplication.net.model.TestRequestModel
import com.example.myapplication.net.model.TestResponseModel
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiService {
    companion object {
        const val COLLECT_SCENE_LIST = "xxxx"
    }

    @POST(value = "/v3/app/api")
    suspend fun queryCollectSceneList(
        @HeaderMap headers: Map<String, String>,
        @Body requestModel: TestRequestModel
    ): ApiResponse<TestResponseModel>


}