package com.example.news.hilt

import android.content.Context
import androidx.room.Room
import com.example.news.networks.ApiService
import com.example.news.networks.MyRepository
import com.example.news.roomDb.MyDao
import com.example.news.roomDb.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kibana.cherryworkproducts.com") // Replace with your API's base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            "my_database"
        ).build()
    }

    @Provides
    fun provideDao(database: MyDatabase): MyDao {
        return database.myDao()
    }

    @Provides
    fun provideMyRepository(dao: MyDao,apiService: ApiService): MyRepository {
        return MyRepository(dao,apiService)
    }
}