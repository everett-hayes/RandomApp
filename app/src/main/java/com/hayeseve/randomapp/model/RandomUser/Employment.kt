package com.hayeseve.randomapp.model.RandomUser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employment (
    @SerializedName("title"     ) var title    : String? = null,
    @SerializedName("key_skill" ) var keySkill : String? = null
) : Parcelable
