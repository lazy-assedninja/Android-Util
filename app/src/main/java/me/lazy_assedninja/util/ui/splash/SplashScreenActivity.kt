package me.lazy_assedninja.util.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import me.lazy_assedninja.util.library.ui.BaseActivity
import me.lazy_assedninja.util.ui.index.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}