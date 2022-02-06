package me.lazy_assedninja.util.ui.index

import dagger.Subcomponent
import me.lazy_assedninja.util.di.ActivityScope
import me.lazy_assedninja.util.ui.encrypt.di.EncryptComponent
import me.lazy_assedninja.util.ui.util.di.UtilComponent

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@ActivityScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    // Factory to create instances of MainComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun utilComponent(): UtilComponent.Factory
    fun encryptComponent(): EncryptComponent.Factory

    // Classes that can be injected by this Component
    fun inject(activity: MainActivity)
}