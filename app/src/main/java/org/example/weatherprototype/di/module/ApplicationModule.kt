package org.example.weatherprototype.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.example.weatherprototype.BuildConfig
import org.example.weatherprototype.data.api.WeatherApi
import org.example.weatherprototype.network.AuthInterceptor
import org.example.weatherprototype.network.connection.NetworkConnectionInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun provideBaseUrl() = "https://api.openweathermap.org/data/2.5/"

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(networkConnectionInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}