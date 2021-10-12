package com.example.myapplication

class MainRepository(private val serviceInterface: ServiceInterface) {

    suspend fun getAllMovies(searchString: String) = serviceInterface.getSearchResults(searchString)

}