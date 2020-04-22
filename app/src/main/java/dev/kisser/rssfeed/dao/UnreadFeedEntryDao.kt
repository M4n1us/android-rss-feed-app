package dev.kisser.rssfeed.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.kisser.rssfeed.entity.UnreadFeedEntry

@Dao
interface UnreadFeedEntryDao {
    @Query("SELECT * FROM unreadfeedentry ORDER BY pubDate DESC")
    fun getAllObservable(): LiveData<List<UnreadFeedEntry>>

    @Query("SELECT * FROM unreadfeedentry")
    fun getAll(): List<UnreadFeedEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg unreadFeedEntries: UnreadFeedEntry)

    @Delete
    suspend fun delete(unreadFeedEntry: UnreadFeedEntry)
}
