package dev.kisser.rssfeed.sync

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prof.rssparser.Parser
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.db.RoomDb
import dev.kisser.rssfeed.entity.Feed
import dev.kisser.rssfeed.entity.UnreadFeedEntry
import dev.kisser.rssfeed.ui.*
import dev.kisser.rssfeed.util.RFC822
import dev.kisser.rssfeed.util.collapseToAsciiSum
import dev.kisser.rssfeed.util.epochStart
import dev.kisser.rssfeed.util.fullFormat
import kotlinx.coroutines.coroutineScope
import java.util.*

class SyncWorker(val context: Context, workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = coroutineScope {

        try{
            val newNotifications = sync(context)
            if (newNotifications.isNotEmpty()) showNotification(context)
        } catch (e: Exception){
            Log.e("SyncWorker","Failed to sync: ${e.stackTrace}")
            Result.failure()
        }


        Result.success()
    }
}

fun showNotification(context: Context){
    val database = RoomDb.getDatabase(context)
    val unreadFeedEntryDao = database.unreadFeedEntryDao()

    val unreadEntries = unreadFeedEntryDao.getAll()

    val intent = Intent(context, MainView::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    intent.putExtra("started_from","syncWorkerNotification")

    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val notifications: MutableList<Pair<Int, Notification>> = mutableListOf()

    unreadEntries.map {
        val title = it.title
        val dateString = fullFormat.format(it.pubDate)
        val id = (title + dateString).collapseToAsciiSum()
        val urlIntent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(it.url))
        val pendingUrlIntent: PendingIntent = PendingIntent.getActivity(context, 0, urlIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        notifications.add(Pair(id, NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_rss_feed_icon)
            .setContentTitle(title)
            .setContentText(dateString)
            .setContentIntent(pendingUrlIntent)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
            .setGroup(NOTIFICATION_GROUP)
            .setAutoCancel(true)
            .build()))
    }

    val message = if (unreadEntries.size > 1) "${unreadEntries.size} unread entries" else "One unread entry"

    val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_rss_feed_icon)
        .setContentTitle(message)
        .setContentText(message)
        .setGroup(NOTIFICATION_GROUP)
        .setGroupSummary(true)
        .setContentIntent(pendingIntent)
        .build()

    NotificationManagerCompat.from(context).apply {
        notify(NOTIFICATION_SUMMARY_URI.collapseToAsciiSum(),summaryNotification)
        notifications.map {
            notify(it.first, it.second)
        }

    }
}

suspend fun sync(context: Context): List<UnreadFeedEntry>{
    val database = RoomDb.getDatabase(context)
    val feedDao = database.feedDao()
    val unreadFeedEntryDao = database.unreadFeedEntryDao()

    val parser = Parser()

    feedDao.getAll().map { feed ->
        val articleList = parser.getChannel(feed.feedUrl)
        val entries: MutableList<UnreadFeedEntry> = mutableListOf<UnreadFeedEntry>()
        articleList.articles.filter { article ->
            val pubDate = article.pubDate
            when (pubDate) {
                null -> true
                else -> {
                    val date = RFC822.parse(pubDate)
                    date?.after(feed.lastEntryDate) ?: true
                }
            }
        }.map {
            val title = it.title
            val link = it.link
            val pubDate = it.pubDate
            if (title != null && link != null && pubDate != null) {
                entries.add(UnreadFeedEntry(0, title, link, RFC822.parse(pubDate) ?: epochStart))
            }
        }

        val newestEntry: UnreadFeedEntry? = entries.maxBy { it.pubDate }


        val lastBuildDate = when (val buildDate = articleList.lastBuildDate) {
            null -> epochStart
            else -> RFC822.parse(buildDate)
        }

        val lastEntryDate = newestEntry?.pubDate ?: feed.lastEntryDate

        val updatedFeed = Feed(feed.feedUrl, lastBuildDate, lastEntryDate, articleList.title)

        feedDao.update(updatedFeed)
        unreadFeedEntryDao.insertAll(*entries.toTypedArray())
    }

    return unreadFeedEntryDao.getAll()
}
