package dev.kisser.rssfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.kisser.rssfeed.R


class FeedManagement : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Feed Management"
        val view = inflater.inflate(R.layout.fragment_feed_management, container, false)
        view.findViewById<FloatingActionButton>(R.id.addFeed).setOnClickListener{
            val dialog = AddFeedDialog()
            dialog.show(activity?.supportFragmentManager, "AddFeedDialogFragment")
        }
        return view
    }
}
