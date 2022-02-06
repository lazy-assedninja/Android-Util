package me.lazy_assedninja.util.ui.util.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.lazy_assedninja.util.di.ViewModelKey
import me.lazy_assedninja.util.ui.util.UtilViewModel

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Suppress("unused")
@Module
abstract class UtilModule {

    @Binds
    @IntoMap
    @ViewModelKey(UtilViewModel::class)
    abstract fun bindUtilViewModel(utilViewModel: UtilViewModel): ViewModel
}