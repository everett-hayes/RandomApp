package com.hayeseve.randomapp.model.RandomUser

import com.google.gson.annotations.SerializedName

data class RandomUser (
    @SerializedName("id"                      ) var id                    : Int?          = null,
    @SerializedName("uid"                     ) var uid                   : String?       = null,
    @SerializedName("password"                ) var password              : String?       = null,
    @SerializedName("first_name"              ) var firstName             : String?       = null,
    @SerializedName("last_name"               ) var lastName              : String?       = null,
    @SerializedName("username"                ) var username              : String?       = null,
    @SerializedName("email"                   ) var email                 : String?       = null,
    @SerializedName("avatar"                  ) var avatar                : String?       = null,
    @SerializedName("gender"                  ) var gender                : String?       = null,
    @SerializedName("phone_number"            ) var phoneNumber           : String?       = null,
    @SerializedName("social_insurance_number" ) var socialInsuranceNumber : String?       = null,
    @SerializedName("date_of_birth"           ) var dateOfBirth           : String?       = null,
    @SerializedName("employment"              ) var employment            : Employment?   = Employment(),
    @SerializedName("address"                 ) var address               : Address?      = Address(),
    @SerializedName("credit_card"             ) var creditCard            : CreditCard?   = CreditCard(),
    @SerializedName("subscription"            ) var subscription          : Subscription? = Subscription()

)