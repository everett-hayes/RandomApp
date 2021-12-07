package com.hayeseve.randomapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hayeseve.randomapp.databinding.ActivityDessertBinding
import com.hayeseve.randomapp.error.ErrorHandle
import com.hayeseve.randomapp.model.RandomDessert
import com.hayeseve.randomapp.service.RandomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.full.memberProperties

class RandomDessertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDessertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDessertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra("NEW", false)) {
            // get my new one and potentially store it in adapter
            callServiceForDessert();
        } else {
            // get the old one I sent along
            intent.getParcelableExtra<RandomDessert>("DES")?.let { bindDessert(it, false) }
        }
    }

    private fun bindDessert(dessert : RandomDessert, isNew : Boolean) {

        if (isNew) {
            binding.saveButton.visibility = View.VISIBLE;
            binding.saveButton.setOnClickListener {
                saveDessert(dessert)
            }
        }

        for (prop in RandomDessert::class.memberProperties) {
            if (prop.name != "id" && prop.name != "uid") {
                val tv = TextView(this)
                tv.setText("${prop.name} = ${prop.get(dessert)}")
                binding.dessertList.addView(tv);
            }
        }
    }

    private fun callServiceForDessert() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://random-data-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val randomService = retrofit.create(RandomService::class.java)
        val randomCall = randomService.getSingleDessert()

        randomCall.enqueue(object : Callback<RandomDessert> {
            override fun onFailure(call: Call<RandomDessert>, t: Throwable) {
                ErrorHandle.handleError(this@RandomDessertActivity, t.localizedMessage);
            }
            override fun onResponse(call: Call<RandomDessert>, response: Response<RandomDessert>) {
                if (response.code() >= 300) {
                    ErrorHandle.handleAPIError(this@RandomDessertActivity, response.code());
                } else {
                    var dessertResult : RandomDessert? = response.body()
                    if (dessertResult != null) {
                        bindDessert(dessertResult, true)
                    }
                }
            }
        })
    }

    private fun saveDessert(dessertToSave : RandomDessert) {
        // send back so it can be added to adapter
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("DES", dessertToSave)
        }
        setResult(Activity.RESULT_OK, intent);
        finish()
    }
}