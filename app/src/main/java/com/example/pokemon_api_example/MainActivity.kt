package com.example.pokemon_api_example

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
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

        val listView = findViewById<ListView>(R.id.main_listview)

        // loading data from API
        val service = RetrofitClientInstance.retrofitInstance?.create(GetPokemon::class.java)
        val call = service?.getPage()
        var i = 1

        val pokemonAdapter = PokemonAdapter(this, pokemonList = allPokemon)

        listView.adapter = pokemonAdapter


        call?.enqueue(object : Callback<PokemonList> {
            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error reading JSON", Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                Log.i("onResponse", i++.toString() + " pages parsed")
                val body = response.body()
                val pokemon: List<PokemonDTO>? = body?.results

                if (pokemon != null) {
                    allPokemon.addAll(pokemon)
                    pokemonAdapter.notifyDataSetChanged()
                }

                if (body?.next != null)
                {
                    val nextCall = service.getNextPage(body.next)!!
                    nextCall.enqueue(this)
                }
            }
        })
    }

    private class PokemonAdapter(context : Context, pokemonList : List<PokemonDTO>) : BaseAdapter()
    {
        private val mContext : Context = context

        private val pokemon = pokemonList

        fun getMatchedPokemon(query : String) : List<PokemonDTO>
        {
            val regexPattern = Regex("$query.*")
            val queryMatches = mutableListOf<PokemonDTO>()
            for(poke in pokemon)
            {
                if (poke.pokemonName.matches(regex = regexPattern))
                {
                    queryMatches.add(poke)
                }

            }

            return queryMatches
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val row = layoutInflater.inflate(R.layout.row_main, parent, false)

            val nameTextView = row.findViewById<TextView>(R.id.name)
            nameTextView.text = pokemon[position].pokemonName

            val positionTextView = row.findViewById<TextView>(R.id.url)
            positionTextView.text = "Pokemon ID: $position"
            return row
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return pokemon.size
        }

    }
}
