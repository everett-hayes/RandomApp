package com.hayeseve.randomapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomDessert (
    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("uid"     ) var uid     : String? = null,
    @SerializedName("variety" ) var variety : String? = null,
    @SerializedName("topping" ) var topping : String? = null,
    @SerializedName("flavor"  ) var flavor  : String? = null
) : Parcelable
