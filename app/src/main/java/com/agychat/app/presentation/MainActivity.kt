package com.agychat.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.agychat.app.R
import com.agychat.app.databinding.ActivityMainBinding
import com.agychat.app.presentation.terminal.TerminalSheetController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var terminalSheetController: TerminalSheetController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.terminalBottomSheet.root)
        // bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN // hide initially if desired
        
        terminalSheetController = TerminalSheetController(binding.terminalBottomSheet.terminalView)
        terminalSheetController.setup()
    }
}
