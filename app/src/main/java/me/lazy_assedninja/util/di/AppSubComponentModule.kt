package me.lazy_assedninja.util.di

import dagger.Module
import me.lazy_assedninja.util.ui.index.MainComponent

/**
 * This module tells a Component which are its SubComponents
 */
@Module(subcomponents = [MainComponent::class])
class AppSubComponentModule