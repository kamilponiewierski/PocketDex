package com.example.pokemon_api_example

import android.widget.Toast
import com.example.pokemon_api_example.dto.PokemonDTO
import com.example.pokemon_api_example.dto.PokemonList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GetPokemon {
    @GET ("/api/v2/pokemon")
    fun getPage(): Call<PokemonList>?

    @GET
    fun getNextPage(@Url url : String) : Call<PokemonList>?
}