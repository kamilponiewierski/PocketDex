package com.example.pokemon_api_example

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance{

    private var retrofit : Retrofit? = null
    private val BASE_URL = "https://pokeapi.co/"

    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null)
            {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }

}