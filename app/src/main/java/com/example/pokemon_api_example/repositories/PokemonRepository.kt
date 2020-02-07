package com.example.pokemon_api_example.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pokemon_api_example.PokemonApi
import com.example.pokemon_api_example.PokemonRetrofitClientInstance
import com.example.pokemon_api_example.dto.PokemonDTO
import com.example.pokemon_api_example.dto.PokemonFullDTO
import com.example.pokemon_api_example.dto.PokemonList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PokemonRepository private constructor(){
    private val dataSet = ArrayList<PokemonFullDTO>()
    private val service = PokemonRetrofitClientInstance.retrofitInstance?.create(PokemonApi::class.java)

    companion object
    {
        val instance: PokemonRepository by lazy {PokemonRepository()}
    }


    fun getPokemons() : MutableLiveData<List<PokemonFullDTO>> {
        setPokemons()

        val data : MutableLiveData<List<PokemonFullDTO>> = MutableLiveData()
        data.value = dataSet

        return data
    }

    private fun setPokemons() {
        if (dataSet.isNotEmpty())
            return

        val call = service?.getFirstPage()

        call?.enqueue(object : Callback<PokemonList> {
            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                Log.e("API error", "Error reading JSON")
            }

            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                Log.i("loadingDataFromAPI", "page parsed")
                val body = response.body()
                val pokemonUrls: List<PokemonDTO>? = body?.results

                if (pokemonUrls != null) {
                    addFullPokemonList(pokemonUrls)
                }

                if (body?.next != null)
                {
                    val nextCall = service?.getNextPage(body.next)!!
                    nextCall.enqueue(this)
                }
            }
        })

    }

    private fun addFullPokemonList(pokemonUrls : List<PokemonDTO>)
    {
        Log.i("loadingDataFromAPI", "adding pokemons to dataSet")

        for (pokemon in pokemonUrls)
        {
            addSingleFullPokemon(pokemon.url)
        }
    }

    private fun addSingleFullPokemon(url : String)
    {
        val call = service?.getFullPokemon(url)

        call?.enqueue(object : Callback<PokemonFullDTO>{
            override fun onFailure(call: Call<PokemonFullDTO>, t: Throwable) {
                Log.e("API error", "Error getting data about a Pokemon")
            }

            override fun onResponse(
                call: Call<PokemonFullDTO>,
                response: Response<PokemonFullDTO>
            ) {
                val body = response.body()
                if (body != null)
                {
                    val pokemon = PokemonFullDTO(body.name, body.id, body.sprites)
                    dataSet.add(pokemon)
                }
            }
        })

    }
}