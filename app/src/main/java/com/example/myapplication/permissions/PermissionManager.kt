package com.example.myapplication.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionManager {

    companion object {
        const val REQUEST_PERMISSION_CODE = 100

        fun hasAudioPermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
            } else {
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            }
        }

        fun requestAudioPermission(launcher: ActivityResultLauncher<String>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(Manifest.permission.READ_MEDIA_AUDIO)
            } else {
                launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
}