package com.keshogroup.template.data.alphavantage.models

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json       = Json { allowStructuredMapKeys = true }
// val ticker5Min = json.parse(Ticker5Min.serializer(), jsonString)

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.keshogroup.template.data.common.entities.Ticker5MinEntity
import kotlinx.serialization.*
import java.util.TreeMap


/****  @SerializedName("Meta Data") //gson VS @SerializedName("Time Series (5min)") //json ***********/
/***********We are using a gson parser in retrofit so we must used the gson annotations******************/
@Serializable
data class Ticker5Min(
    @SerializedName("Meta Data")
    val metaData: MetaData,

    @SerializedName("Time Series (5min)")
    val timeSeries5Min: Map<String, TimeSeries5Min>,
) {
    fun toEntity(): Ticker5MinEntity {
        return Ticker5MinEntity(
            the1Information = this.metaData.the1Information,
            the2Symbol = this.metaData.the2Symbol,
            the3LastRefreshed = this.metaData.the3LastRefreshed,
            the4Interval = this.metaData.the4Interval,
            the5OutputSize = this.metaData.the5OutputSize,
            the6TimeZone = this.metaData.the6TimeZone,
        )
    }

    companion object {
        fun empty(): Ticker5Min {
            return Ticker5Min(
                metaData = MetaData(
                    the2Symbol = "",
                    the1Information = "",
                    the3LastRefreshed = "",
                    the4Interval = "",
                    the5OutputSize = "",
                    the6TimeZone = ""
                ),
                timeSeries5Min = TreeMap<String, TimeSeries5Min>()
            )
        }
    }

}

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
