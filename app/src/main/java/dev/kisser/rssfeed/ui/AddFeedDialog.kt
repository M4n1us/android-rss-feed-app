package dev.kisser.rssfeed.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import dev.kisser.rssfeed.R

class AddFeedDialog: DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: AddFeedDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface AddFeedDialogListener {
        fun onDialogPositiveClick(url: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AddFeedDialogListener so we can send events to the host
            listener = context as AddFeedDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.add_url, null)

            builder
                .setView(view)
                .setMessage(R.string.dialog_feed_url)
                .setPositiveButton(
                    R.string.dialog_add,
                    DialogInterface.OnClickListener { _, _ ->
                        val url = view.findViewById<EditText>(R.id.feedUrl).text.toString()
                        listener.onDialogPositiveClick(url)
                    })
                .setNegativeButton(
                    R.string.dialog_cancel,
                    DialogInterface.OnClickListener { _, _ ->
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
