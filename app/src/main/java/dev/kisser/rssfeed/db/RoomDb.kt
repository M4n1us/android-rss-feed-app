package dev.kisser.rssfeed.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.kisser.rssfeed.dao.FeedDao
import dev.kisser.rssfeed.dao.UnreadFeedEntryDao
import dev.kisser.rssfeed.entity.Feed
import dev.kisser.rssfeed.entity.UnreadFeedEntry

@Database(entities = arrayOf(Feed::class, UnreadFeedEntry::class), version = 1)
@TypeConverters(dev.kisser.rssfeed.db.TypeConverters::class)
abstract class RoomDb: RoomDatabase() {
    abstract fun feedDao(): FeedDao
    abstract fun unreadFeedEntryDao(): UnreadFeedEntryDao
}
