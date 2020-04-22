package dev.kisser.rssfeed.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.kisser.rssfeed.entity.UnreadFeedEntry

@Dao
interface UnreadFeedEntryDao {
    @Query("SELECT * FROM unreadfeedentry")
    fun getAll(): List<UnreadFeedEntry>

    @Insert
    fun insertAll(vararg unreadFeedEntries: UnreadFeedEntry)

    @Delete
    fun delete(unreadFeedEntry: UnreadFeedEntry)
}
