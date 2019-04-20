package org.niralmehta.camerauploadexample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.niralmehta.camerauploadexample.main.MainActivity

/**
 * Instruct Dagger what Activities to inject, includes the FragmentBuildersModule to inject modules
 */
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    internal abstract fun contributeMainActivity(): MainActivity
}
