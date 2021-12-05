package com.hayeseve.randomapp.model.RandomUser

import com.google.gson.annotations.SerializedName

data class Coordinates (
    @SerializedName("lat" ) var lat : Double? = null,
    @SerializedName("lng" ) var lng : Double? = null
)