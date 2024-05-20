package com.example.myapplication

import android.app.Application
import com.hoko.blur.BuildConfig

import timber.log.Timber


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Inicialización de Timber para logs en la aplicación
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Configuraciones globales, inicialización de librerías, etc.
        // Por ejemplo, inicialización de Analytics, Crashlytics, etc.
    }
}