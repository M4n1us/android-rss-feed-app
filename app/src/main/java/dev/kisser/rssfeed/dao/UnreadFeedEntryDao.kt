package dev.kisser.rssfeed.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.kisser.rssfeed.entity.UnreadFeedEntry

@Dao
interface UnreadFeedEntryDao {
    @Query("SELECT * FROM unreadfeedentry ORDER BY pubDate DESC")
    fun getAll(): LiveData<List<UnreadFeedEntry>>

    @Insert
    fun insertAll(vararg unreadFeedEntries: UnreadFeedEntry)

    @Delete
    fun delete(unreadFeedEntry: UnreadFeedEntry)
}
