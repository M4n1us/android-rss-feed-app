package dev.kisser.rssfeed.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.work.*
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.sync.SyncWorker
import java.util.concurrent.TimeUnit


class MainView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setSupportActionBar(findViewById(R.id.toolbar))

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("dev.kisser.rssfeed.syncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest)

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

}
