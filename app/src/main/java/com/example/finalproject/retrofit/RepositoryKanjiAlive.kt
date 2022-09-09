package com.example.finalproject.retrofit

import com.example.finalproject.Resource
import com.example.finalproject.retrofit.pojo.PojoSingleKanjiDetail
import javax.inject.Inject


class RepositoryKanjiAlive @Inject constructor(private val api: KanjiAliveService) {

    suspend fun getSingleDetail(kanji: String): Resource<PojoSingleKanjiDetail> {
        return try {
            val response = api.getSingleDetail(kanji = kanji)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Failure("Response is not Successful !")
            }
        } catch (e: Exception) {
            Resource.Failure(e.message ?: "Check Your Internet Connect : D")
        }
    }

}