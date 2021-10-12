package com.example.myapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceInterface {

   @GET("names/users")
   suspend fun getSearchResults(@Query("name") searchString: String) : Response<List<ResponseItem>>

}