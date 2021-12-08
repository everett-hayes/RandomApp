package com.hayeseve.randomapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hayeseve.randomapp.databinding.ActivityUserBinding
import com.hayeseve.randomapp.error.ErrorHandle
import com.hayeseve.randomapp.model.RandomHipster
import com.hayeseve.randomapp.model.RandomUser.RandomUser
import com.hayeseve.randomapp.service.AvatarService
import com.hayeseve.randomapp.service.RandomService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import kotlin.reflect.full.memberProperties
import android.graphics.BitmapFactory

import android.graphics.Bitmap




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
        } else {
            getAvatar(user.avatar!!)
        }

        for (prop in RandomUser::class.memberProperties) {
            if (prop.name != "id" && prop.name != "uid") {
                val tv = TextView(this)
                tv.setText("${prop.name} = ${prop.get(user)}")
                tv.setTextColor(Color.parseColor("#FF000000"))
                binding.userList.addView(tv);
            }
        }
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
                        getAvatar(userResult.avatar!!);
                    }
                }
            }
        })
    }

    private fun getAvatar(avatarUrl : String) {

        val url : Uri = Uri.parse(avatarUrl);

        val baseUrl = "https://${url.authority}/"
        val path = url.path;
        val size = url.getQueryParameter("size");
        val set = url.getQueryParameter("set");

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val imageService = retrofit.create(AvatarService::class.java);
        val imageCall = imageService.getAvatarPicture(path!!, size!!, set!!);

        imageCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                ErrorHandle.handleError(this@RandomUserActivity, t.localizedMessage);
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() >= 300) {
                    ErrorHandle.handleAPIError(this@RandomUserActivity, response.code());
                } else {
                    val bmp = BitmapFactory.decodeStream(response.body()!!.byteStream());
                    binding.userAvatar.setImageBitmap(bmp);
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