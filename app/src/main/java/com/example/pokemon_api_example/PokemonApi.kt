package com.example.pokemon_api_example

import com.example.pokemon_api_example.dto.PokemonFullDTO
import com.example.pokemon_api_example.dto.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApi {
    @GET ("/api/v2/pokemon")
    fun getPage(): Call<PokemonList>?

    @GET
    fun getNextPage(@Url url : String) : Call<PokemonList>?

    @GET
    fun getImage(@Url url : String) : String

    @GET
    fun getFullPokemon(@Url url : String) : Call<PokemonFullDTO>
}