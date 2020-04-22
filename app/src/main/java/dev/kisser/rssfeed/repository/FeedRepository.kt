package dev.kisser.rssfeed.repository

import androidx.lifecycle.LiveData
import dev.kisser.rssfeed.dao.FeedDao
import dev.kisser.rssfeed.entity.Feed

class FeedRepository(private val feedDao: FeedDao) {

    val allFeeds: LiveData<List<Feed>> = feedDao.getAll()

    suspend fun insert(feed: Feed) {
        feedDao.insertAll(feed)
    }

    suspend fun update(feed: Feed){
        feedDao.update(feed)
    }

    suspend fun delete(feed: Feed){
        feedDao.delete(feed)
    }
}
