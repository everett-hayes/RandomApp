package com.hayeseve.randomapp.service

import com.hayeseve.randomapp.model.RandomAddress
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface AvatarService {

    // https://robohash.org/eaqueiurerepudiandae.png?size=300x300&set=set1
    @GET
    fun getAvatarPicture(@Url url: String, @Query("size") size : String, @Query("set") set : String) : Call<ResponseBody>
}