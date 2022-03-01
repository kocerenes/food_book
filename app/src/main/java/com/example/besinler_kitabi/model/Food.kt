package com.example.besinler_kitabi.model

import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("isim")
    val name : String?,
    @SerializedName("kalori")
    val calori : String?,
    @SerializedName("karbonhidrat")
    val carbonhydrate : String?,
    @SerializedName("protein")
    val protein : String?,
    @SerializedName("yag")
    val oil : String?,
    @SerializedName("gorsel")
    val image : String?
)