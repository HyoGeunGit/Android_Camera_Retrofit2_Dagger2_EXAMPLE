package org.niralmehta.camerauploadexample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.niralmehta.camerauploadexample.modules.upload.fragment.ScreenUpload
import org.niralmehta.camerauploadexample.screens.ScreenCamera

/**
 * Instruct Dagger what fragments to inject
 */
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributesCameraScreen(): ScreenCamera

    @ContributesAndroidInjector
    internal abstract fun contributesUploadScreen(): ScreenUpload
}
