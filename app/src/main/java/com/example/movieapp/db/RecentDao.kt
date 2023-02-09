package com.example.movieapp.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentDao {
    //user 가지고 오기
    @Query("SELECT * FROM Recent")
    fun getRecentList(): LiveData<List<Recent>>

    //user 넣기
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecent(recent: Recent)

}