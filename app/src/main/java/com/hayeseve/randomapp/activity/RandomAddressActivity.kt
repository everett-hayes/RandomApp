package com.hayeseve.randomapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hayeseve.randomapp.R
import com.hayeseve.randomapp.databinding.ActivityAddressBinding
import com.hayeseve.randomapp.error.ErrorHandle
import com.hayeseve.randomapp.model.RandomAddress
import com.hayeseve.randomapp.service.RandomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomAddressActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityAddressBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra("NEW", false)) {
            // get my new one and potentially store it in adapter
            callServiceForAddress();
        } else {
            // get the old one I sent along
            intent.getParcelableExtra<RandomAddress>("ADR")?.let { bindAddress(it, false) }
        }
    }

    private fun bindAddress(address : RandomAddress, isNew : Boolean) {

        if (isNew) {
            binding.saveButton.visibility = View.VISIBLE;
            binding.saveButton.setOnClickListener {
                saveAddress(address)
            }
        }

        binding.addressText.setText(address.fullAddress);

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun callServiceForAddress() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://random-data-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val randomService = retrofit.create(RandomService::class.java)
        val randomCall = randomService.getSingleAddress()

        randomCall.enqueue(object : Callback<RandomAddress> {
            override fun onFailure(call: Call<RandomAddress>, t: Throwable) {
                ErrorHandle.handleError(this@RandomAddressActivity, t.localizedMessage);
            }
            override fun onResponse(call: Call<RandomAddress>, response: Response<RandomAddress>) {
                if (response.code() >= 300) {
                    ErrorHandle.handleAPIError(this@RandomAddressActivity, response.code());
                } else {
                    var addressResult : RandomAddress? = response.body()
                    if (addressResult != null) {
                        bindAddress(addressResult, true)
                    }
                }
            }
        })
    }

    private fun saveAddress(addressToSave : RandomAddress) {
        // send back so it can be added to adapter
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("ADR", addressToSave)
        }
        setResult(Activity.RESULT_OK, intent);
        finish()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}