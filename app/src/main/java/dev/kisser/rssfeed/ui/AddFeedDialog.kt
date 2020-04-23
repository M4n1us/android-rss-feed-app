package dev.kisser.rssfeed.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import dev.kisser.rssfeed.R

class AddFeedDialog(val feedManagement: FeedManagement, val parent: ViewGroup?): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_url, parent)

        builder
            .setView(view)
            .setMessage(R.string.dialog_feed_url)
            .setPositiveButton(
                R.string.dialog_add,
                DialogInterface.OnClickListener { _, _ ->
                    val url = view.findViewById<EditText>(R.id.feedUrl).text.toString()
                    feedManagement.handleDialogReturn(url)
                })
            .setNegativeButton(
                R.string.dialog_cancel,
                DialogInterface.OnClickListener { _, _ ->
                })

        return builder.create()
    }
}
