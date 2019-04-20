package org.niralmehta.camerauploadexample.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import org.niralmehta.camerauploadexample.CameraUploadExample
import javax.inject.Singleton

/**
 * Application Component for Dagger
 */
@Singleton
@Component(
    modules = [
        (AndroidInjectionModule::class),
        (AndroidSupportInjectionModule::class),
        (AppModule::class),
        (ActivityBuildersModule::class)]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(cameraUploadExample: CameraUploadExample)
}
