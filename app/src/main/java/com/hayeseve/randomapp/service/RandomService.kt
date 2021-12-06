package com.hayeseve.randomapp.service

import com.hayeseve.randomapp.model.RandomAddress
import com.hayeseve.randomapp.model.RandomCrypto
import com.hayeseve.randomapp.model.RandomDessert
import com.hayeseve.randomapp.model.RandomHipster
import com.hayeseve.randomapp.model.RandomUser.RandomUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomService {

    // https://random-data-api.com/api/address/random_address
    @GET("api/address/random_address")
    fun getSingleAddress() : Call<RandomAddress>

    // https://random-data-api.com/api/users/random_user
    @GET("api/users/random_user")
    fun getSingleUser() : Call<RandomUser>

    // https://random-data-api.com/api/dessert/random_dessert
    @GET("api/dessert/random_dessert")
    fun getSingleDessert() : Call<RandomDessert>

    // https://random-data-api.com/api/crypto/random_crypto
    @GET("api/crypto/random_crypto")
    fun getSingleCrypto() : Call<RandomCrypto>

    // https://random-data-api.com/api/hipster/random_hipster_stuff
    @GET("api/hipster/random_hipster_stuff")
    fun getSingleHipster() : Call<RandomHipster>

    // https://random-data-api.com/api/address/random_address
    @GET("api/address/random_address")
    fun getManyAddress(@Query("size") size : Int) : Call<List<RandomAddress>>
}