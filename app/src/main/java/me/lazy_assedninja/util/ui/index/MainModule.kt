package me.lazy_assedninja.util.ui.index

import dagger.Module
import me.lazy_assedninja.util.ui.encrypt.di.EncryptComponent
import me.lazy_assedninja.util.ui.util.di.UtilComponent

/**
 * Definition of dependencies, tell Dagger how to provide classes.
 */
@Module(
    subcomponents = [
        UtilComponent::class,
        EncryptComponent::class
    ]
)
class MainModule