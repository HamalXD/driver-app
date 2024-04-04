package com.example.newapp.ui.newtaxidriver.common.network

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.seentechs.newtaxidriver.R
import com.seentechs.newtaxidriver.common.dependencies.component.AppComponent
import com.seentechs.newtaxidriver.common.dependencies.component.DaggerAppComponent
import com.seentechs.newtaxidriver.common.dependencies.module.ApplicationModule
import com.seentechs.newtaxidriver.common.dependencies.module.NetworkModule
import com.seentechs.newtaxidriver.common.util.CommonMethods
import java.util.*

class AppController : Application() {
    private var locale: Locale? = null

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        appContext = this
        instance = this
        setLocale()

        AppCOmpatDelegate.setCompatVectorFromResourcesEnabled(true)

        appComponent = DaggerAppComponent.builder().applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule(getString(R.string.apiBaseUrl))).build()

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }

    private fun setLocale() {
        locale = Locale("en")
        Locale.setDefault(locale)
        val configuration = baseContext.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
        } else
            configuration.setLocale(locale)
        baseContext.createConfigurationContext(configuration)
    }

    override fun attatchBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        val TAG = AppController::class.java.simpleName

        @get:Synchronized
        var instance: AppController? = null
            private set
        lateinit private var appComponent: AppComponent
        lateinit var appContext: Context

        fun getAppComponent(): AppComponent {
            CommonMethods.DebuggableLogV("non", "null" + appComponent)
            return appComponent
        }

        val context: Context?
            get() = instance
    }
}