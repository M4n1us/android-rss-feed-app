package dev.kisser.rssfeed.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.kisser.rssfeed.repository.FeedRepository
import dev.kisser.rssfeed.repository.UnreadFeedEntryRepository
import kotlinx.coroutines.coroutineScope

class SyncWorker(context: Context, workerParameters: WorkerParameters, val feedRepository: FeedRepository, val unreadFeedEntryRepository: UnreadFeedEntryRepository)
    : CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result = coroutineScope {

        feedRepository.

        Result.success()
    }
}
