package com.example.finalproject.retrofit

import com.example.finalproject.retrofit.pojo.PojoSingleKanjiDetail
import retrofit2.Response
import retrofit2.http.GET

interface KanjiAliveService {
    @GET("advanced")
    suspend fun getSingleKanjiDetail():Response<PojoSingleKanjiDetail>
}