package com.alextruck.cob_test_task.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.alextruck.cob_test_task.SPLASH_DELAY
import com.alextruck.cob_test_task.databinding.SplashLayoutBinding
import com.alextruck.cob_test_task.ui.base.BaseActivity
import com.alextruck.cob_test_task.ui.component.albums.AlbumsListActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by AlextrucK
 */

@AndroidEntryPoint
class SplashActivity : BaseActivity(){

    private lateinit var binding: SplashLayoutBinding

    override fun initViewBinding() {
        binding = SplashLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {
    }

    private fun navigateToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val nextScreenIntent = Intent(this, AlbumsListActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}