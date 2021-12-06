package com.hayeseve.randomapp.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hayeseve.randomapp.databinding.ActivityMainBinding
import android.widget.ListView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.hayeseve.randomapp.adapter.RecyclerAdapter
import com.hayeseve.randomapp.model.RandomAddress
import com.hayeseve.randomapp.model.RandomUser.RandomUser


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerAdapter(this);
        binding.randomList.adapter = adapter;

        binding.addRandomButton.setOnClickListener {
            createNewRandom()
        }
    }

    fun createNewRandom() {

        val builder = AlertDialog.Builder(this)
            .setTitle("What Kind of Random Data?")

        val items = arrayOf("Address", "User", "Crypto", "Dessert", "Hipster")

        builder.setSingleChoiceItems(items, -1) { dialog, which -> }

        builder.setPositiveButton("Add") { dialog, which ->
            val lw: ListView = (dialog as AlertDialog).listView
            val position = lw.getCheckedItemPosition()

            if (position >= 0) {
                when (items[position]) {
                    "Address" -> goToAddress(true, null)
                    "User" -> goToUser(true, null)
                }
            }
        }.setNegativeButton("Cancel") { dialog, which -> }

        val alert = builder.create();
        alert.show()
    }

    fun goToAddress(isNew : Boolean, address : RandomAddress?) {

        if (isNew) {
            // start the activity and grab result for adapter
            val intent = Intent(this, RandomAddressActivity::class.java);
            intent.putExtra("NEW", true);
            startAddressForResult.launch(intent)
        } else {
            // get the existing address and pass to activity
            val intent = Intent(this, RandomAddressActivity::class.java);
            intent.putExtra("NEW", false);
            intent.putExtra("ADR", address);
            startActivity(intent);
        }
    }

    private val startAddressForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val createdAddress = intent?.getParcelableExtra<RandomAddress>("ADR");
            if (createdAddress != null) {
                adapter.addItem(createdAddress)
            };
        }
    }

    fun goToUser(isNew : Boolean, user : RandomUser?) {

        if (isNew) {
            // start the activity and grab result for adapter
            val intent = Intent(this, RandomUserActivity::class.java);
            intent.putExtra("NEW", true);
            startUserForResult.launch(intent)
        } else {
            // get the existing address and pass to activity
            val intent = Intent(this, RandomUserActivity::class.java);
            intent.putExtra("NEW", false);
            intent.putExtra("USR", user);
            startActivity(intent);
        }
    }

    private val startUserForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val createdUser = intent?.getParcelableExtra<RandomUser>("USR");
            if (createdUser != null) {
                adapter.addItem(createdUser)
            };
        }
    }
}