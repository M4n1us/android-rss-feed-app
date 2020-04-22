package dev.kisser.rssfeed.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.kisser.rssfeed.dao.FeedDao
import dev.kisser.rssfeed.dao.UnreadFeedEntryDao
import dev.kisser.rssfeed.entity.Feed
import dev.kisser.rssfeed.entity.UnreadFeedEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

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
            context: Context,
            scope: CoroutineScope
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
                ).addCallback(RoomDbCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class RoomDbCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.feedDao()) }
            }
        }

        suspend fun populateDatabase(feedDao: FeedDao) {
            feedDao.deleteAll()

            val testFeed = Feed("http://wuxiaworld.com/feed", Date(), Date(), "testfeed")
            feedDao.insertAll(testFeed)
            val testFeed2 = Feed("http://wuxiaworld.com/feed2", Date(), Date(), "testfeed2")
            feedDao.insertAll(testFeed2)

            Log.d(this.javaClass.name, "Initialised Feeds")
        }
    }
}
