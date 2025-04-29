package com.keshogroup.template.data.alphavantage.models

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json       = Json { allowStructuredMapKeys = true }
// val ticker5Min = json.parse(Ticker5Min.serializer(), jsonString)

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.*


/****  @SerializedName("Meta Data") //gson VS @SerializedName("Time Series (5min)") //json ***********/
/***********We are using a gson parser in retrofit so we must used the gson annotations******************/
@Serializable
data class Ticker5Min(
    @SerializedName("Meta Data")
    val metaData: MetaData,

    @SerializedName("Time Series (5min)")
    val timeSeries5Min: Map<String, TimeSeries5Min>
)

@Serializable
data class MetaData(
    @SerializedName("1. Information")
    val the1Information: String,

    @SerializedName("2. Symbol")
    val the2Symbol: String,

    @SerializedName("3. Last Refreshed")
    val the3LastRefreshed: String,

    @SerializedName("4. Interval")
    val the4Interval: String,

    @SerializedName("5. Output Size")
    val the5OutputSize: String,

    @SerializedName("6. Time Zone")
    val the6TimeZone: String
)

@Serializable
data class TimeSeries5Min(
    @SerializedName("1. open")
    val the1Open: String,

    @SerializedName("2. high")
    val the2High: String,

    @SerializedName("3. low")
    val the3Low: String,

    @SerializedName("4. close")
    val the4Close: String,

    @SerializedName("5. volume")
    val the5Volume: String
)
