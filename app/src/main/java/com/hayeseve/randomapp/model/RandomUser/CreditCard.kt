package com.hayeseve.randomapp.model.RandomUser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditCard (
    @SerializedName("cc_number" ) var ccNumber : String? = null
) : Parcelable
