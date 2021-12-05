package com.hayeseve.randomapp.model.RandomUser

import com.google.gson.annotations.SerializedName

data class Address (
    @SerializedName("city"           ) var city          : String?      = null,
    @SerializedName("street_name"    ) var streetName    : String?      = null,
    @SerializedName("street_address" ) var streetAddress : String?      = null,
    @SerializedName("zip_code"       ) var zipCode       : String?      = null,
    @SerializedName("state"          ) var state         : String?      = null,
    @SerializedName("country"        ) var country       : String?      = null,
    @SerializedName("coordinates"    ) var coordinates   : Coordinates? = Coordinates()
)
