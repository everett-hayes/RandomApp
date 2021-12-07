package com.hayeseve.randomapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hayeseve.randomapp.databinding.ActivityHipsterBinding
import com.hayeseve.randomapp.error.ErrorHandle
import com.hayeseve.randomapp.model.RandomDessert
import com.hayeseve.randomapp.model.RandomHipster
import com.hayeseve.randomapp.service.RandomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.full.memberProperties

class RandomHipsterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHipsterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHipsterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra("NEW", false)) {
            // get my new one and potentially store it in adapter
            callServiceForHipster();
        } else {
            // get the old one I sent along
            intent.getParcelableExtra<RandomHipster>("HIP")?.let { bindHipster(it, false) }
        }
    }

    private fun bindHipster(hipster : RandomHipster, isNew : Boolean) {

        if (isNew) {
            binding.saveButton.visibility = View.VISIBLE;
            binding.saveButton.setOnClickListener {
                saveHipster(hipster)
            }
        }

        for (prop in RandomHipster::class.memberProperties) {
            if (prop.name != "id" && prop.name != "uid") {
                val tv = TextView(this)
                tv.setText("${prop.name} = ${prop.get(hipster)}")
                binding.hipsterList.addView(tv);
            }
        }
    }

    private fun callServiceForHipster() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://random-data-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val randomService = retrofit.create(RandomService::class.java)
        val randomCall = randomService.getSingleHipster()

        randomCall.enqueue(object : Callback<RandomHipster> {
            override fun onFailure(call: Call<RandomHipster>, t: Throwable) {
                ErrorHandle.handleError(this@RandomHipsterActivity, t.localizedMessage);
            }
            override fun onResponse(call: Call<RandomHipster>, response: Response<RandomHipster>) {
                if (response.code() >= 300) {
                    ErrorHandle.handleAPIError(this@RandomHipsterActivity, response.code());
                } else {
                    var hispterResult : RandomHipster? = response.body()
                    if (hispterResult != null) {
                        bindHipster(hispterResult, true)
                    }
                }
            }
        })
    }

    private fun saveHipster(hipsterToSave : RandomHipster) {
        // send back so it can be added to adapter
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("HIP", hipsterToSave)
        }
        setResult(Activity.RESULT_OK, intent);
        finish()
    }
}