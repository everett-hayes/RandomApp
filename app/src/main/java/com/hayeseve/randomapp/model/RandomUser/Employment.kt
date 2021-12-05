package com.hayeseve.randomapp.model.RandomUser

import com.google.gson.annotations.SerializedName

data class Employment (
    @SerializedName("title"     ) var title    : String? = null,
    @SerializedName("key_skill" ) var keySkill : String? = null
)
