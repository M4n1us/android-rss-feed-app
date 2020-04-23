package dev.kisser.rssfeed.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prof.rssparser.Parser
import dev.kisser.rssfeed.db.RoomDb
import dev.kisser.rssfeed.entity.Feed
import dev.kisser.rssfeed.entity.UnreadFeedEntry
import dev.kisser.rssfeed.util.RFC822
import dev.kisser.rssfeed.util.epochStart
import kotlinx.coroutines.coroutineScope

class SyncWorker(val context: Context, workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = coroutineScope {

        sync(context)

        Result.success()
    }
}

suspend fun sync(context: Context){
    val database = RoomDb.getDatabase(context)
    val feedDao = database.feedDao()
    val unreadFeedEntryDao = database.unreadFeedEntryDao()

    val parser = Parser()

    feedDao.getAll().map {feed ->
        val articleList = parser.getChannel(feed.feedUrl)
        val entries : MutableList<UnreadFeedEntry> = mutableListOf<UnreadFeedEntry>()
        articleList.articles.filter { article->
            val pubDate = article.pubDate
            when(pubDate){
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
            if(title != null && link != null && pubDate != null){
                entries.add(UnreadFeedEntry(0, title, link, RFC822.parse(pubDate) ?: epochStart))
            }
        }

        val newestEntry: UnreadFeedEntry? = entries.maxBy { it.pubDate }


        val lastBuildDate = when(val buildDate = articleList.lastBuildDate){
            null -> epochStart
            else ->RFC822.parse(buildDate)
        }

        val lastEntryDate = newestEntry?.pubDate ?: feed.lastEntryDate

        val updatedFeed = Feed(feed.feedUrl, lastBuildDate, lastEntryDate, articleList.title)

        feedDao.update(updatedFeed)
        unreadFeedEntryDao.insertAll(*entries.toTypedArray())
    }
}
