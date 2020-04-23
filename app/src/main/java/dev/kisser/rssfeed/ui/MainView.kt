package dev.kisser.rssfeed.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.work.*
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.sync.SyncWorker
import dev.kisser.rssfeed.util.collapseToAsciiSum
import java.util.concurrent.TimeUnit

const val SYNC_WORKER_NAME = "dev.kisser.rssfeed.syncWork"
const val CHANNEL_ID = "dev.kisser.rssfeed.notification"
const val NOTIFICATION_GROUP = "dev.kisser.rssfeed.new_entries"
const val NOTIFICATION_SUMMARY_URI = "dev.kisser.rssfeed.summary"

class MainView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setSupportActionBar(findViewById(R.id.toolbar))

        createNotificationChannel()
        launchSyncWorker()

        val intentSource = getIntent().getStringExtra("started_from") ?: "-"
        if(intentSource.equals("syncWorkerNotification")){
            NotificationManagerCompat.from(this).apply {
                cancel(NOTIFICATION_SUMMARY_URI.collapseToAsciiSum())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> openFeedManagement()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFeedManagement(): Boolean {
        val controller = findNavController(R.id.nav_host_fragment)
        if(controller.currentDestination?.id == R.id.feedFragment2){
            controller.navigate(R.id.action_feedFragment2_to_feedManagement2)
            return true
        }
        return false
    }

    private fun launchSyncWorker(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            SYNC_WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest)
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}
