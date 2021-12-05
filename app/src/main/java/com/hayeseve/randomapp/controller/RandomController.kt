package com.hayeseve.randomapp.controller

import android.content.Context
import android.widget.Toast
import com.hayeseve.randomapp.model.RandomAddress
import com.hayeseve.randomapp.service.RandomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomController() {

    private lateinit var context: Context
    private val retrofit: Retrofit
    private val randomService: RandomService

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://random-data-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        randomService = retrofit.create(RandomService::class.java)
    }

    constructor(context: Context) : this() {
        this.context = context
    }

    fun getSingleRandomAddress() : RandomAddress? {

        val addressCall = randomService.getSingleAddress()
        var addressCallResult : RandomAddress? = null

        addressCall.enqueue(object : Callback<RandomAddress> {
            override fun onFailure(call: Call<RandomAddress>, t: Throwable) {
                onAPIFailure(-1);
            }
            override fun onResponse(call: Call<RandomAddress>, response: Response<RandomAddress>) {
                if (response.code() >= 300) {
                    onAPIFailure(response.code());
                }
                addressCallResult = response.body()
            }
        })

        return addressCallResult
    }

    fun onAPIFailure(code : Int) {
        Toast.makeText(context, "API Failure Code : $code", Toast.LENGTH_LONG).show();
    }
}