package com.keshogroup.template.data.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.keshogroup.template.data.common.entities.Ticker5MinEntity
import com.keshogroup.template.data.common.entities.User

@Database(entities = [User::class, Ticker5MinEntity::class], version = 1)
abstract class CommonDatabase : RoomDatabase() {
    abstract fun commonDAO(): CommonDAO
}

object CommonDB {

    var db: CommonDatabase? = null

    fun setupDB(applicationContext: Context): CommonDatabase {
        if (db == null) {
            createDB(applicationContext)
        }
        return db!!
    }

    fun getDB(): CommonDatabase {
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