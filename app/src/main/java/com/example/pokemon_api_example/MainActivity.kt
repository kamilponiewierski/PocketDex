package com.example.pokemon_api_example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon_api_example.dto.PokemonDTO
import com.example.pokemon_api_example.dto.PokemonList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var allPokemon = ArrayList<PokemonDTO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitClientInstance.retrofitInstance?.create(GetPokemon::class.java)
        val call = service?.getPage()
        val pokemons = mutableListOf<PokemonDTO>()

        call?.enqueue(object : Callback<PokemonList> {
            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error reading JSON", Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                val body = response.body()
                val pokemon: List<PokemonDTO>? = body?.results

                if (pokemon != null) {
                    pokemons.addAll(pokemon)
                }
            }
        })

        assert(pokemons.size > 10)
    }
}