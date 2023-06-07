package com.example.edistynytmobiiliohjelmointi2023lapinamk.datatypes.cityweather

import com.google.gson.annotations.SerializedName


data class Rain (

  @SerializedName("1h" ) var oneHour : Double? = null

)