package com.example.myapplication


import org.koin.dsl.module
import retrofit2.Retrofit

val mainModule = module {

    factory {
        serviceInterface(get())
    }

    factory {
        MainRepository(
            get()
        )
    }

}

fun serviceInterface(retrofit: Retrofit.Builder): ServiceInterface {
    return retrofit.baseUrl(AppConstants.BASE_URL).build().create(ServiceInterface::class.java)
}


