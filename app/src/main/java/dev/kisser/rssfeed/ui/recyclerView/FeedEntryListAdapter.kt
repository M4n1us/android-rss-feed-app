package dev.kisser.rssfeed.ui.recyclerView

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.entity.UnreadFeedEntry
import dev.kisser.rssfeed.util.fullFormat
import dev.kisser.rssfeed.viewmodel.FeedEntryViewModel

class FeedEntryListAdapter internal constructor(
    activity: FragmentActivity
) : RecyclerView.Adapter<FeedEntryListAdapter.FeedEntryViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(activity)
    private val feedEntryViewModel = ViewModelProvider(activity).get(FeedEntryViewModel::class.java)
    private var unreadEntries = emptyList<UnreadFeedEntry>()

    inner class FeedEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val feedEntryTitle: TextView = itemView.findViewById(R.id.feedEntryTitle)
        val feedEntryUrl: TextView = itemView.findViewById(R.id.feedEntryUrl)
        val feedEntryPubDate: TextView = itemView.findViewById(R.id.feedEntryPubDate)
        val openInBrowser: ImageButton = itemView.findViewById(R.id.openInBrowser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedEntryViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_feed_entry_item, parent, false)
        return FeedEntryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedEntryViewHolder, position: Int) {
        val current = unreadEntries[position]
        holder.feedEntryTitle.text = current.title
        holder.feedEntryUrl.text = current.url
        holder.feedEntryPubDate.text = fullFormat.format(current.pubDate)
        holder.openInBrowser.setOnClickListener{
            feedEntryViewModel.delete(current)
            val open = Intent(Intent.ACTION_VIEW).setData(Uri.parse(current.url))
            inflater.context.startActivity(open)
        }
    }

    internal fun setUnreadEntries(entries: List<UnreadFeedEntry>){
        this.unreadEntries = entries
        notifyDataSetChanged()
    }

    override fun getItemCount() = unreadEntries.size
}
