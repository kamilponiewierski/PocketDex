package com.example.pokemon_api_example

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pokemon_api_example.adapters.PokemonAdapter
import com.example.pokemon_api_example.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mMainActivityViewModel : MainActivityViewModel
    private lateinit var pokemonAdapter : PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mMainActivityViewModel =
            ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mMainActivityViewModel.init()

        mMainActivityViewModel.getPokemons()
            ?.observe(this, Observer { pokemonAdapter.notifyDataSetChanged() })

        pokemonAdapter = PokemonAdapter(this, mMainActivityViewModel)
        val listView = findViewById<ListView>(R.id.main_listview)

        listView.adapter = pokemonAdapter
    }
}
