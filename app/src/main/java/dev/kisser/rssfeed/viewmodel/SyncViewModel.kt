package dev.kisser.rssfeed.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.kisser.rssfeed.sync.sync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SyncViewModel(application: Application) : AndroidViewModel(application)  {
    val context: Context


    init {
        context = application
    }

    fun syncItems() = viewModelScope.launch(Dispatchers.IO) {
        sync(context)
    }
}
