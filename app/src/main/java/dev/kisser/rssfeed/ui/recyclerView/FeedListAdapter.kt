package dev.kisser.rssfeed.ui.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.entity.Feed

class FeedListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<FeedListAdapter.FeedViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var feeds = emptyList<Feed>()

    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val feedTitle: TextView = itemView.findViewById(R.id.feedTitle)
        val lastEntryDate: TextView = itemView.findViewById(R.id.lastEntryDate)
        val deleteItem: ImageButton = itemView.findViewById(R.id.deleteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_feed_item, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = feeds[position]
        holder.feedTitle.text = current.feedTitle
        holder.lastEntryDate.text = current.lastEntryDate.toString()
        //holder.deleteItem.setOnClickListener() TODO("Figure out how to delete")
    }

    internal fun setFeeds(feeds: List<Feed>){
        this.feeds = feeds
        notifyDataSetChanged()
    }

    override fun getItemCount() = feeds.size
}
