package com.hayeseve.randomapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomCrypto (
    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("uid"    ) var uid    : String? = null,
    @SerializedName("md5"    ) var md5    : String? = null,
    @SerializedName("sha1"   ) var sha1   : String? = null,
    @SerializedName("sha256" ) var sha256 : String? = null
) : Parcelable
