package me.lazy_assedninja.sample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.sample.ui.encrypt.EncryptViewModel
import me.lazy_assedninja.sample.ui.utils_list.UtilsViewModel
import me.lazy_assedninja.sample.view_model.ViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UtilsViewModel::class)
    abstract fun bindUtilsViewModel(utilsViewModel: UtilsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EncryptViewModel::class)
    abstract fun bindEncryptViewModel(encryptViewModel: EncryptViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}