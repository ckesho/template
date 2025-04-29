package com.keshogroup.template.data.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.keshogroup.template.data.alphavantage.models.Ticker5Min
import com.keshogroup.template.data.common.models.User

@Database(entities = [User::class], version = 1)
abstract class CommonDatabase : RoomDatabase() {
    abstract fun commonDAO(): CommonDAO
}

object CommonDB {

    var db: CommonDatabase? = null

    fun getDB(applicationContext: Context): CommonDatabase {
        if (db == null) {
            createDB(applicationContext)
        }
        return db!!
    }

    private fun createDB(applicationContext: Context) {
        db = Room.databaseBuilder(
            context = applicationContext,
            CommonDatabase::class.java,
            name = "common_database"
        ).build()
    }

}