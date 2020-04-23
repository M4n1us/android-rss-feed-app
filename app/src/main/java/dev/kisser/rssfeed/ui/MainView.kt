package dev.kisser.rssfeed.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Parser
import dev.kisser.rssfeed.R
import dev.kisser.rssfeed.entity.Feed
import dev.kisser.rssfeed.ui.recyclerView.FeedListAdapter
import dev.kisser.rssfeed.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking


class MainView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setSupportActionBar(toolbar)
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
