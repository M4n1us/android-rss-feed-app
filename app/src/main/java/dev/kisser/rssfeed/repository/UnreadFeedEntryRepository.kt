package dev.kisser.rssfeed.repository

import androidx.lifecycle.LiveData
import dev.kisser.rssfeed.dao.UnreadFeedEntryDao
import dev.kisser.rssfeed.entity.UnreadFeedEntry

class UnreadFeedEntryRepository(private val unreadFeedEntryDao: UnreadFeedEntryDao)  {

    val allUnreadEntries: LiveData<List<UnreadFeedEntry>> = unreadFeedEntryDao.getAllObservable()

    suspend fun getAll(): List<UnreadFeedEntry>{
        return unreadFeedEntryDao.getAll()
    }

    suspend fun insertAll(vararg unreadFeedEntry: UnreadFeedEntry){
        unreadFeedEntryDao.insertAll(*unreadFeedEntry)
    }

    suspend fun delete(unreadFeedEntry: UnreadFeedEntry){
        unreadFeedEntryDao.delete(unreadFeedEntry)
    }
}
