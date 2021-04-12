package com.characterexploretest.retrofit

import com.characterexploretest.model.Actor
import retrofit2.http.GET
import retrofit2.Call

interface Api {
    @GET("characters")
    fun getAddresses(): Call<MutableList<Actor>>
}