package com.hayeseve.randomapp.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hayeseve.randomapp.databinding.ActivityMainBinding
import android.widget.Toast
import android.widget.ListView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            if (position > 0) {
                Toast.makeText(this, "Clicked on ${items[position]}", Toast.LENGTH_LONG).show()
            }
        }.setNegativeButton("Cancel") { dialog, which -> }

        val alert = builder.create();
        alert.show()
    }
}