package dev.kisser.rssfeed.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.kisser.rssfeed.dao.FeedDao
import dev.kisser.rssfeed.dao.UnreadFeedEntryDao
import dev.kisser.rssfeed.entity.Feed
import dev.kisser.rssfeed.entity.UnreadFeedEntry

@Database(entities = [Feed::class, UnreadFeedEntry::class], version = 1, exportSchema = true)
@TypeConverters(dev.kisser.rssfeed.db.TypeConverters::class)
abstract class RoomDb: RoomDatabase() {
    abstract fun feedDao(): FeedDao
    abstract fun unreadFeedEntryDao(): UnreadFeedEntryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getDatabase(
            context: Context
        ): RoomDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java,
                    "feed_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
