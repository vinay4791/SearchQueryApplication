package com.example.myapplication

import com.example.myapplication.AppConstants.BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val NETWORK_TIMEOUT = 30L

val networkModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { moshi() }
    single { provideRetrofitBuilder(get(), get()) }

}

fun getBaseUrl(): String {
    return BASE_URL
}

fun moshi(): Moshi {
    return Moshi.Builder().build()
}

fun provideRetrofitBuilder(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit.Builder {
    val retrofitBuilder = Retrofit.Builder()
    retrofitBuilder.baseUrl(getBaseUrl())
    retrofitBuilder.client(okHttpClient)
    retrofitBuilder .addConverterFactory(MoshiConverterFactory.create(moshi))
    return retrofitBuilder
}

fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
    httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    httpClientBuilder.readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
    return httpClientBuilder.build()
}

fun  provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}