package com.vfalin.jet.di.moduls

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vfalin.jet.db.AppDatabase
import com.vfalin.jet.network.LoggingInterceptor
import com.vfalin.jet.network.RetrofitBase
import com.vfalin.jet.network.services.MembersService
import com.vfalin.jet.repositories.MembersActivityRepositoryImpl
import com.vfalin.jet.utils.ConnectivityLiveData
import com.vfalin.jet.viewmodel.factories.MembersActivityViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitBase.BASE_URL)
        .client(
            OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor(LoggingInterceptor())
                .build()
        ).addConverterFactory(
            MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
        ).build()
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app-database"
    ).build()

    init {
        bind(Context::class.java).toInstance(context)
        bind(ConnectivityLiveData::class.java).singleton()

        bind(MembersActivityViewModelFactory::class.java).toInstance(
            MembersActivityViewModelFactory(
                MembersActivityRepositoryImpl(
                    retrofit.create(MembersService::class.java),
                    db.membersDao()
                )
            )
        )
    }
}