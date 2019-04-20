package org.niralmehta.camerauploadexample

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.niralmehta.camerauploadexample.di.AppInjector
import timber.log.Timber
import javax.inject.Inject

class CameraUploadExample : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppInjector.init(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? =
        dispatchingAndroidInjector

}