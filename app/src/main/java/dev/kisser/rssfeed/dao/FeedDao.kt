package dev.kisser.rssfeed.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.kisser.rssfeed.entity.Feed

@Dao
interface FeedDao {
    @Query("SELECT * FROM feed ORDER BY feedTitle ASC")
    fun getAll(): LiveData<List<Feed>>

    @Query("SELECT * FROM feed WHERE feedUrl = :url")
    fun findByUrl(url: String): Feed

    @Insert
    fun insertAll(vararg feeds: Feed)

    @Update
    fun update(feed: Feed)

    @Delete
    fun delete(feed: Feed)
}
