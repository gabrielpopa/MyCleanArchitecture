package com.gabrielpopa.myapplication.data.second.remote.dto

import com.google.gson.annotations.SerializedName

data class SecondRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)