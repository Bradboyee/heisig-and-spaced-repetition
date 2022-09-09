package com.example.finalproject.retrofit

import com.example.finalproject.BuildConfig
import com.example.finalproject.retrofit.pojo.PojoSingleKanjiDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KanjiAliveService {
    @GET("/api/public/kanji/{kanji}")
    suspend fun getSingleDetail(
        @Path("kanji")kanji: String,
        @Query("rapidapi-key") key :String = BuildConfig.API_KEY
    ): Response<PojoSingleKanjiDetail>

}