package me.lazy_assedninja.sample.di

import dagger.Module
import dagger.Provides
import me.lazy_assedninja.sample.db.UtilsDAO
import me.lazy_assedninja.sample.db.UtilsDatabase
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideUtilsDAO(db: UtilsDatabase): UtilsDAO {
        return db.utilsDAO()
    }
}