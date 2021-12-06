package com.hayeseve.randomapp.model.RandomUser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Subscription (
    @SerializedName("plan"           ) var plan          : String? = null,
    @SerializedName("status"         ) var status        : String? = null,
    @SerializedName("payment_method" ) var paymentMethod : String? = null,
    @SerializedName("term"           ) var term          : String? = null
) : Parcelable