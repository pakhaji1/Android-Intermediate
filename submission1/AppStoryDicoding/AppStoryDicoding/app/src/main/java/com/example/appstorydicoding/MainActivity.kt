package com.example.appstorydicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.appstorydicoding.databinding.ActivityMainBinding
import com.example.appstorydicoding.local.UserPreferences
import com.example.appstorydicoding.local.UserToken
import com.example.appstorydicoding.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userModel: UserToken
    private lateinit var userPreferences: UserPreferences

    private var token : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val listStoryFragment = ListStoryFragment()
        val fragment = fragmentManager.findFragmentByTag(ListStoryFragment::class.java.simpleName)

        if (fragment !is ListStoryFragment) {
            Log.d("Fragment", "Story Fragment" + ListStoryFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.home, listStoryFragment, ListStoryFragment::class.java.simpleName)
                .commit()
        }

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        token = userModel.token.toString()

        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }

    private fun logout() {
        userModel.token = ""
        userPreferences.setUser(userModel)
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    companion object {
        const val EXTRA_KEY = "EXTRA_KEY"
    }
}