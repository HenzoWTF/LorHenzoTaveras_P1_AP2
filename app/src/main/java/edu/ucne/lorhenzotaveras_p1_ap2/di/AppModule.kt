package edu.ucne.lorhenzotaveras_p1_ap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.lorhenzotaveras_p1_ap2.data.local.database.Parcial1Db
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): Parcial1Db{
        return Room.databaseBuilder(
            context,
            Parcial1Db::class.java,
        "Algo_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

}