package dev.kisser.rssfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.ui.recyclerView.FeedListAdapter


class FeedManagement : Fragment() {

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
        val adapter = FeedListAdapter(requireActivity().applicationContext)
        feedRecyclerView.adapter = adapter
        feedRecyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        view.findViewById<FloatingActionButton>(R.id.addFeed).setOnClickListener{
            val dialog = AddFeedDialog()
            requireActivity().supportFragmentManager?.let { it1 -> dialog.show(it1, "AddFeedDialogFragment") }
        }
        return view
    }
}
