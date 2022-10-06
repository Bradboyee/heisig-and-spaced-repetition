package com.thepparat.heisigwithspacedrepetition.retrofit

import com.thepparat.heisigwithspacedrepetition.BuildConfig
import com.thepparat.heisigwithspacedrepetition.retrofit.pojo.PojoSingleKanjiDetail
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