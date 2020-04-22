package dev.kisser.rssfeed.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.kisser.rssfeed.entity.Feed

@Dao
interface FeedDao {
    @Query("SELECT * FROM feed ORDER BY feedTitle ASC")
    fun getAllObservable(): LiveData<List<Feed>>

    @Query("SELECT * FROM feed")
    fun getAll(): List<Feed>

    @Query("SELECT * FROM feed WHERE feedUrl = :url")
    fun findByUrl(url: String): Feed

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg feeds: Feed)

    @Update
    suspend fun update(feed: Feed)

    @Delete
    suspend fun delete(feed: Feed)

    @Query("DELETE FROM feed")
    suspend fun deleteAll()
}
