package com.hayeseve.randomapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hayeseve.randomapp.databinding.ActivityCryptoBinding
import com.hayeseve.randomapp.databinding.ActivityUserBinding
import com.hayeseve.randomapp.model.RandomUser.RandomUser

class RandomCryptoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCryptoBinding

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCryptoBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (intent.getBooleanExtra("NEW", false)) {
//            // get my new one and potentially store it in adapter
//            callServiceForUser();
//        } else {
//            // get the old one I sent along
//            intent.getParcelableExtra<RandomUser>("CRP")?.let { bindUser(it, false) }
//        }
//    }
//
//    private fun bindUser(user : RandomUser, isNew : Boolean) {
//
//        if (isNew) {
//            binding.saveButton.visibility = View.VISIBLE;
//            binding.saveButton.setOnClickListener {
//                saveUser(user)
//            }
//        }
//
//        binding.userText.setText(user.username.toString())
//    }
}