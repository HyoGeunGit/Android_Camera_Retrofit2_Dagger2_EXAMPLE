package org.niralmehta.camerauploadexample.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.niralmehta.camerauploadexample.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Module to instruct Dagger what to inject
 */
@Module(
    includes = [
        ViewModelModule::class
    ]
)
internal class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        val uri = BuildConfig.URI
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient().newBuilder()
        // Default Read and Connection timeout is 8000 millis
        client.connectTimeout(6000, TimeUnit.MILLISECONDS)
        client.readTimeout(6000, TimeUnit.MILLISECONDS)
        client.interceptors().add(logging)

        return Retrofit.Builder()
            .baseUrl(uri)
            .client(client.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
