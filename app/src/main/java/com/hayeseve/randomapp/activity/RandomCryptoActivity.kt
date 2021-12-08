package com.hayeseve.randomapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hayeseve.randomapp.databinding.ActivityCryptoBinding
import com.hayeseve.randomapp.error.ErrorHandle
import com.hayeseve.randomapp.model.RandomAddress
import com.hayeseve.randomapp.model.RandomCrypto
import com.hayeseve.randomapp.model.RandomUser.RandomUser
import com.hayeseve.randomapp.service.RandomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.full.memberProperties

class RandomCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCryptoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra("NEW", false)) {
            // get my new one and potentially store it in adapter
            callServiceForCrypto();
        } else {
            // get the old one I sent along
            intent.getParcelableExtra<RandomCrypto>("CRP")?.let { bindCrypto(it, false) }
        }
    }

    private fun bindCrypto(crypto : RandomCrypto, isNew : Boolean) {

        if (isNew) {
            binding.saveButton.visibility = View.VISIBLE;
            binding.saveButton.setOnClickListener {
                saveCrypto(crypto)
            }
        }

        for (prop in RandomCrypto::class.memberProperties) {
            if (prop.name != "id" && prop.name != "uid") {
                val tv = TextView(this)
                tv.setText("${prop.name} = ${prop.get(crypto)}")
                tv.setTextColor(Color.parseColor("#FF000000"))
                binding.cryptoList.addView(tv);
            }
        }
    }

    private fun callServiceForCrypto() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://random-data-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val randomService = retrofit.create(RandomService::class.java)
        val randomCall = randomService.getSingleCrypto()

        randomCall.enqueue(object : Callback<RandomCrypto> {
            override fun onFailure(call: Call<RandomCrypto>, t: Throwable) {
                ErrorHandle.handleError(this@RandomCryptoActivity, t.localizedMessage);
            }
            override fun onResponse(call: Call<RandomCrypto>, response: Response<RandomCrypto>) {
                if (response.code() >= 300) {
                    ErrorHandle.handleAPIError(this@RandomCryptoActivity, response.code());
                } else {
                    var cryptoResult : RandomCrypto? = response.body()
                    if (cryptoResult != null) {
                        bindCrypto(cryptoResult, true)
                    }
                }
            }
        })
    }

    private fun saveCrypto(cryptoToSave : RandomCrypto) {
        // send back so it can be added to adapter
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("CRP", cryptoToSave)
        }
        setResult(Activity.RESULT_OK, intent);
        finish()
    }
}