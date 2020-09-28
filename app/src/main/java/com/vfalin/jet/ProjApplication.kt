package com.vfalin.jet

import android.app.Application
import com.vfalin.jet.di.Scopes
import com.vfalin.jet.di.moduls.AppModule
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration

class ProjApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initToothpick()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initToothpick() {
        Toothpick.setConfiguration(
            if (BuildConfig.DEBUG) Configuration.forDevelopment().preventMultipleRootScopes()
            else Configuration.forProduction()
        )
        Toothpick.openScope(Scopes.APP).installModules(AppModule(this))
    }
}