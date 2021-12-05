package com.hayeseve.randomapp.model

import com.google.gson.annotations.SerializedName

data class RandomAddress (

    @SerializedName("id"                ) var id               : Int?    = null,
    @SerializedName("uid"               ) var uid              : String? = null,
    @SerializedName("city"              ) var city             : String? = null,
    @SerializedName("street_name"       ) var streetName       : String? = null,
    @SerializedName("street_address"    ) var streetAddress    : String? = null,
    @SerializedName("secondary_address" ) var secondaryAddress : String? = null,
    @SerializedName("building_number"   ) var buildingNumber   : String? = null,
    @SerializedName("mail_box"          ) var mailBox          : String? = null,
    @SerializedName("community"         ) var community        : String? = null,
    @SerializedName("zip_code"          ) var zipCode          : String? = null,
    @SerializedName("zip"               ) var zip              : String? = null,
    @SerializedName("postcode"          ) var postcode         : String? = null,
    @SerializedName("time_zone"         ) var timeZone         : String? = null,
    @SerializedName("street_suffix"     ) var streetSuffix     : String? = null,
    @SerializedName("city_suffix"       ) var citySuffix       : String? = null,
    @SerializedName("city_prefix"       ) var cityPrefix       : String? = null,
    @SerializedName("state"             ) var state            : String? = null,
    @SerializedName("state_abbr"        ) var stateAbbr        : String? = null,
    @SerializedName("country"           ) var country          : String? = null,
    @SerializedName("country_code"      ) var countryCode      : String? = null,
    @SerializedName("latitude"          ) var latitude         : Double? = null,
    @SerializedName("longitude"         ) var longitude        : Double? = null,
    @SerializedName("full_address"      ) var fullAddress      : String? = null
)
