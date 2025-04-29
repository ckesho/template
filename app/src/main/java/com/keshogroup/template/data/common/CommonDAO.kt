package com.keshogroup.template.data.common

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.keshogroup.template.data.alphavantage.models.Ticker5Min
import com.keshogroup.template.data.common.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface CommonDAO {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<User>

    @Query(
        "SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    suspend fun findByName(first: String, last: String): User

    @Insert
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user")
    fun getAllAsFlow(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIdsAsFlow(userIds: IntArray): Flow<List<User>>

    @Query(
        "SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByNameAsFlow(first: String, last: String): Flow<User>


//    @Insert
//    suspend fun insertAllTickers(vararg users: Ticker5Min)
}