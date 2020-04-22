package dev.kisser.rssfeed.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dev.kisser.rssfeed.R
import kotlinx.android.synthetic.main.activity_main3.*


class MainView : AppCompatActivity(), AddFeedDialog.AddFeedDialogListener {

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

    override fun onDialogPositiveClick(url: String) {
        Log.d(this.localClassName, url)

    }

}
