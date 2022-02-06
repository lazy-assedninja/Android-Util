package me.lazy_assedninja.util.ui.index

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import me.lazy_assedninja.util.R
import me.lazy_assedninja.util.application.MyApplication
import me.lazy_assedninja.util.library.ui.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()

        // When using activities, inject Dagger in the activity's onCreate() method
        // before calling super.onCreate() to avoid issues with fragment restoration.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initNavigation()
    }

    private fun initDagger() {
        // Creates an instance of main component by grabbing the factory from the app graph
        mainComponent = (application as MyApplication).appComponent
            .mainComponent().create()

        // Injects this activity to the just created MainComponent
        mainComponent.inject(this)
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(
            findViewById(R.id.toolbar),
            navController,
            appBarConfiguration
        )
    }
}