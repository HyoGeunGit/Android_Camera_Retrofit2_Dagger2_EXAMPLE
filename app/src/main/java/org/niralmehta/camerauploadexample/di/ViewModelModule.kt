package org.niralmehta.camerauploadexample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.niralmehta.camerauploadexample.modules.upload.viewmodel.UploadViewModel
import org.niralmehta.camerauploadexample.viewmodel.ViewModelFactory

/**
 * Interface responsible for creating ViewModel Instances
 */
@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UploadViewModel::class)
    internal abstract fun bindUploadViewModel(uploadViewModel: UploadViewModel): ViewModel

}
