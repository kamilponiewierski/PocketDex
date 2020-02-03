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
    fun getNextPage(@Url url : String)

//    fun getAllPokemon() : Call<PokemonDTO>
//    {
//        var pokemons = mutableListOf<PokemonDTO>()
//        var hasNext : Boolean = true
//        var next : String = ""
//
//        val call : Call<PokemonList>? = this.getFirstPage()
//        do {
//
//            @GET
//
//
//            call?.enqueue(object : Callback<PokemonList>
//            {
//                override fun onFailure(call: Call<PokemonList>, t: Throwable)
//                {
//                    System.err.print("Error reading JSON");
//                }
//
//                override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>)
//                {
//                    val results = response.body()?.results
//                    if (results != null) {
//                        pokemons.addAll(results)
//                        hasNext = response.body()?.next != null
//
//                        if (hasNext)
//                            next = response.body()?.next!!
//                    }
//                }
//            })
//
//        } while(hasNext)
//
//    }
}