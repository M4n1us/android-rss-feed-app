package dev.kisser.rssfeed.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.kisser.rssfeed.entity.Feed
import java.util.*

@Dao
interface FeedDao {
    @Query("SELECT * FROM feed")
    fun getAll(): List<Feed>

    @Query("SELECT * FROM feed WHERE feedUrl = :url")
    fun findByUrl(url: String): Feed

    @Query("UPDATE Feed SET lastBuildDate = :lastBuildDate WHERE feedUrl = :url")
    fun updateLastBuildDate(url: String, lastBuildDate: Date)

    @Insert
    fun insertAll(vararg feeds: Feed)

    @Delete
    fun delete(feed: Feed)
}
