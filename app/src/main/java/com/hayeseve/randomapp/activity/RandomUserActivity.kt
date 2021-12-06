package com.hayeseve.randomapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hayeseve.randomapp.databinding.ActivityUserBinding
import com.hayeseve.randomapp.error.ErrorHandle
import com.hayeseve.randomapp.model.RandomUser.RandomUser
import com.hayeseve.randomapp.service.RandomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra("NEW", false)) {
            // get my new one and potentially store it in adapter
            callServiceForUser();
        } else {
            // get the old one I sent along
            intent.getParcelableExtra<RandomUser>("USR")?.let { bindUser(it, false) }
        }
    }

    private fun bindUser(user : RandomUser, isNew : Boolean) {

        if (isNew) {
            binding.saveButton.visibility = View.VISIBLE;
            binding.saveButton.setOnClickListener {
                saveUser(user)
            }
        }

        binding.userText.setText(user.username.toString())
    }

    private fun callServiceForUser() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://random-data-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val randomService = retrofit.create(RandomService::class.java)
        val randomCall = randomService.getSingleUser()

        randomCall.enqueue(object : Callback<RandomUser> {
            override fun onFailure(call: Call<RandomUser>, t: Throwable) {
                ErrorHandle.handleError(this@RandomUserActivity, t.localizedMessage);
            }
            override fun onResponse(call: Call<RandomUser>, response: Response<RandomUser>) {
                if (response.code() >= 300) {
                    ErrorHandle.handleAPIError(this@RandomUserActivity, response.code());
                } else {
                    var userResult : RandomUser? = response.body()
                    if (userResult != null) {
                        bindUser(userResult, true)
                    }
                }
            }
        })
    }

    private fun saveUser(userToSave : RandomUser) {
        // send back so it can be added to adapter
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("USR", userToSave)
        }
        setResult(Activity.RESULT_OK, intent);
        finish()
    }
}