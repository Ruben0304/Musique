package com.example.myapplication.ui.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.permissions.PermissionManager

class WelcomeActivity : AppCompatActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportActionBar?.hide()
        setContentView(R.layout.activity_welcome)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btnContinuar = findViewById<ImageButton>(R.id.continuar)

        btnContinuar.setOnClickListener{
           continueToHome()
        }



        // Initialize the permission launcher
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                continueToHome()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        btnContinuar.setOnClickListener{
            checkPermissionsAndContinue()
        }


    }
    private fun continueToHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
            com.google.android.material.R.anim.abc_popup_enter,
            com.google.android.material.R.anim.abc_popup_exit
        )
    }

    private fun checkPermissionsAndContinue() {
        if (PermissionManager.hasAudioPermission(this)) {
            continueToHome()
        } else {
            PermissionManager.requestAudioPermission(permissionLauncher)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun hideSystemUI() {
        window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }


}