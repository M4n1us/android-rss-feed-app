package dev.kisser.rssfeed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.kisser.rssfeed.db.RoomDb
import dev.kisser.rssfeed.entity.Feed
import dev.kisser.rssfeed.repository.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FeedRepository
    val allFeeds: LiveData<List<Feed>>

    init {
        val feedDao = RoomDb.getDatabase(application, viewModelScope).feedDao()
        repository = FeedRepository(feedDao)
        allFeeds = repository.allFeeds
    }

    fun insert(feed: Feed) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(feed)
    }

    fun update(feed: Feed) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(feed)
    }

    fun delete(feed: Feed) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(feed)
    }
}
