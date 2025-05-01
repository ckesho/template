package com.keshogroup.template.data.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.keshogroup.template.data.alphavantage.models.MetaData
import com.keshogroup.template.data.alphavantage.models.Ticker5Min
import com.keshogroup.template.data.alphavantage.models.TimeSeries5Min
import java.util.TreeMap

@Entity
data class Ticker5MinEntity(
    val the1Information: String,
    @PrimaryKey
    val the2Symbol: String,

    val the3LastRefreshed: String,

    val the4Interval: String,

    val the5OutputSize: String,

    val the6TimeZone: String
) {
    fun toModel(): Ticker5Min {
        return Ticker5Min(
            metaData = MetaData(
                the1Information = this.the1Information,
                the2Symbol = this.the2Symbol,
                the3LastRefreshed = the3LastRefreshed,
                the4Interval = the4Interval,
                the5OutputSize = the5OutputSize,
                the6TimeZone = the6TimeZone,
            ),
            timeSeries5Min = TreeMap<String, TimeSeries5Min>()
        )
    }
}
