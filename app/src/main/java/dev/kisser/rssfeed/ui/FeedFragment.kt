package dev.kisser.rssfeed.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.sync.SyncWorker
import dev.kisser.rssfeed.sync.sync
import dev.kisser.rssfeed.ui.recyclerView.FeedEntryListAdapter
import dev.kisser.rssfeed.viewmodel.FeedEntryViewModel
import dev.kisser.rssfeed.viewmodel.SyncViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class FeedFragment : Fragment() {
    lateinit var feedEntryViewModel: FeedEntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Feed"
        val view = inflater.inflate(R.layout.fragment_first2, container, false)

        val feedEntryRecyclerView = view.findViewById<RecyclerView>(R.id.feedEntryRecyclerView)
        val adapter = FeedEntryListAdapter(requireActivity())
        feedEntryRecyclerView.adapter = adapter
        feedEntryRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        feedEntryViewModel = ViewModelProvider(requireActivity()).get(FeedEntryViewModel::class.java)
        feedEntryViewModel.allUnreadEntries.observe(requireActivity(), Observer {
                entries -> entries?.let { adapter.setUnreadEntries(it) }
            }
        )

        val syncModel = ViewModelProvider(requireActivity()).get(SyncViewModel::class.java)

        view.findViewById<FloatingActionButton>(R.id.refreshFeed).setOnClickListener {
            Toast.makeText(
                requireContext(),
                R.string.toast_refresh,
                Toast.LENGTH_SHORT).show()
            try{
                syncModel.syncItems()
                Toast.makeText(
                    requireContext(),
                    R.string.toast_completed,
                    Toast.LENGTH_LONG).show()
                syncModel.notifications()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    R.string.toast_connection_error,
                    Toast.LENGTH_LONG).show()
            }

        }

        return view
    }

}
