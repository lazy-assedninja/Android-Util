package me.lazy_assedninja.util.ui.encrypt.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.util.di.ViewModelKey
import me.lazy_assedninja.util.ui.encrypt.EncryptViewModel

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class EncryptModule {

    @Binds
    @IntoMap
    @ViewModelKey(EncryptViewModel::class)
    abstract fun bindEncryptViewModel(encryptViewModel: EncryptViewModel): ViewModel
}