package dev.kisser.rssfeed.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.entity.Feed
import dev.kisser.rssfeed.ui.recyclerView.FeedListAdapter
import dev.kisser.rssfeed.viewmodel.FeedViewModel


class FeedManagement : Fragment() {
    lateinit var feedViewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().title = "Feed Management"
        val view = inflater.inflate(R.layout.fragment_feed_management, container, false)

        val feedRecyclerView = view.findViewById<RecyclerView>(R.id.feedRecyclerView)
        val adapter = FeedListAdapter(requireActivity())
        feedRecyclerView.adapter = adapter
        feedRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        feedViewModel = ViewModelProvider(requireActivity()).get(FeedViewModel::class.java)
        feedViewModel.allFeeds.observe(requireActivity(), Observer {
                feeds -> feeds?.let { adapter.setFeeds(it) }
            }
        )

        view.findViewById<FloatingActionButton>(R.id.addFeed).setOnClickListener{
            val dialog = AddFeedDialog(this)
            requireActivity().supportFragmentManager.let { it1 -> dialog.show(it1, "AddFeedDialogFragment") }
        }
        return view
    }

    fun handleDialogReturn(url: String){
        Log.d(this.javaClass.name, url)
        if(URLUtil.isValidUrl(url)) {
            Log.d(this.javaClass.name, "Url is valid")
            val feed = Feed(url, null, null, null)
            feedViewModel.insert(feed)
        } else {
            Toast.makeText(
                requireContext(),
                R.string.dialog_invalid_url,
                Toast.LENGTH_LONG).show()
        }
    }
}
