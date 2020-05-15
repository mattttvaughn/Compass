package io.github.mattpvaughn.compass.application

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import io.github.mattpvaughn.compass.BuildConfig
import javax.inject.Singleton


@Singleton
open class CustomApplication : Application() {
    init {
        INSTANCE = this
    }

    companion object {
        private var INSTANCE: CustomApplication? = null

        @JvmStatic
        fun get(): CustomApplication = INSTANCE!!
    }

    override fun onCreate() {
        if (STRICT_MODE && BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
//                    choose which ones you want
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectNetwork() // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
            StrictMode.setVmPolicy(
                VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .detectActivityLeaks()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }

        super.onCreate()
    }


}
