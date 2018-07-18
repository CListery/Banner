package com.yh.banner

import android.app.Application

/**
 * Created by Clistery on 18-7-18.
 */
class BannerApp : Application() {

    companion object {
        private lateinit var mInstance: BannerApp

        fun get(): BannerApp {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this@BannerApp
    }

}
