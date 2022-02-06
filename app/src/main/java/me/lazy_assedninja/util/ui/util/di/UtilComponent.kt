package me.lazy_assedninja.util.ui.util.di

import dagger.Subcomponent
import me.lazy_assedninja.util.ui.util.UtilFragment

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@UtilScope
@Subcomponent(modules = [UtilModule::class])
interface UtilComponent {

    // Factory to create instances of UtilComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): UtilComponent
    }

    // Classes that can be injected by this Component
    fun inject(utilFragment: UtilFragment)
}