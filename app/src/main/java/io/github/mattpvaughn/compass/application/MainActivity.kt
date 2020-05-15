package io.github.mattpvaughn.compass.application

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.github.mattpvaughn.compass.R
import io.github.mattpvaughn.compass.databinding.ActivityMainBinding
import io.github.mattpvaughn.compass.features.library.CompassFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(APP_NAME, "MainActivity onCreate()")
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if the app is going to be restored, if not, show the library page
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragNavHost, CompassFragment.newInstance())
                .commit()
        }

    }


    override fun onPause() {
        Log.i(APP_NAME, "MainActivity onPause()")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.i(APP_NAME, "MainActivity onResume()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(APP_NAME, "MainActivity onStart()")
    }

    override fun onStop() {
        Log.i(APP_NAME, "MainActivity onStop()")
        super.onStop()
    }
}
