package me.lazy_assedninja.util.ui.encrypt.di

import dagger.Subcomponent
import me.lazy_assedninja.util.ui.encrypt.EncryptFragment

/**
 * Components that inherit and extend the object graph of a parent component.
 */
@EncryptScope
@Subcomponent(modules = [EncryptModule::class])
interface EncryptComponent {

    // Factory to create instances of EncryptComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): EncryptComponent
    }

    // Classes that can be injected by this Component
    fun inject(encryptFragment: EncryptFragment)
}