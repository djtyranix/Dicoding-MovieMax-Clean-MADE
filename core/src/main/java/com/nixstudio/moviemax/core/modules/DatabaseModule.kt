package com.nixstudio.moviemax.core.modules

import android.app.Application
import androidx.room.Room
import com.nixstudio.moviemax.Secrets
import com.nixstudio.moviemax.core.data.sources.local.room.MovieMaxDao
import com.nixstudio.moviemax.core.data.sources.local.room.MovieMaxDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): MovieMaxDatabase {
        val passphrase: ByteArray =
            SQLiteDatabase.getBytes(Secrets().getPass("com.nixstudio.moviemax").toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(application, MovieMaxDatabase::class.java, "moviemax-db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    fun provideCountriesDao(database: MovieMaxDatabase): MovieMaxDao {
        return database.movieMaxDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}