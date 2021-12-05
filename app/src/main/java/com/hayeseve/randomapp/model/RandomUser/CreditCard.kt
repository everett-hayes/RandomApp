package com.hayeseve.randomapp.model.RandomUser

import com.google.gson.annotations.SerializedName

data class CreditCard (
    @SerializedName("cc_number" ) var ccNumber : String? = null
)
