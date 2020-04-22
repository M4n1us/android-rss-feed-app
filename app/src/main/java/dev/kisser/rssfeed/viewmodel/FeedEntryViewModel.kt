package dev.kisser.rssfeed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.kisser.rssfeed.db.RoomDb
import dev.kisser.rssfeed.entity.UnreadFeedEntry
import dev.kisser.rssfeed.repository.UnreadFeedEntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UnreadFeedEntryRepository
    val allUnreadEntries: LiveData<List<UnreadFeedEntry>>

    init {
        val unreadFeedEntryDao = RoomDb.getDatabase(application, viewModelScope).unreadFeedEntryDao()
        repository = UnreadFeedEntryRepository(unreadFeedEntryDao)
        allUnreadEntries = repository.allUnreadEntries
    }

    fun insert(entry: List<UnreadFeedEntry>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAll(*entry.toTypedArray())
    }

    fun delete(entry: UnreadFeedEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(entry)
    }
}
