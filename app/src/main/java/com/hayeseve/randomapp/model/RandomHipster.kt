package com.hayeseve.randomapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomHipster (
    @SerializedName("id"         ) var id         : Int?         = null,
    @SerializedName("uid"        ) var uid        : String?      = null,
    @SerializedName("word"       ) var word       : String?      = null,
    @SerializedName("words"      ) var words      : List<String> = arrayListOf(),
    @SerializedName("sentence"   ) var sentence   : String?      = null,
    @SerializedName("sentences"  ) var sentences  : List<String> = arrayListOf(),
    @SerializedName("paragraph"  ) var paragraph  : String?      = null,
    @SerializedName("paragraphs" ) var paragraphs : List<String> = arrayListOf()
) : Parcelable
