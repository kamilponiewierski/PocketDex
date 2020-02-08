package com.example.pocketdex

import com.example.pocketdex.dto.PokemonFullDTO
import com.example.pocketdex.dto.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApi {
    @GET ("/api/v2/pokemon")
    fun getFirstPage(): Call<PokemonList>?

    @GET
    fun getNextPage(@Url url : String) : Call<PokemonList>?

    @GET
    fun getFullPokemon(@Url url : String) : Call<PokemonFullDTO>
}