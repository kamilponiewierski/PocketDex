package com.example.pokemon_api_example

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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

        // setting up ViewModel
        mMainActivityViewModel =
            ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mMainActivityViewModel.init()
        mMainActivityViewModel.getPokemons()
            ?.observe(this, Observer { pokemonAdapter.notifyDataSetChanged() })

        // setting up Adapter
        pokemonAdapter = PokemonAdapter(this, mMainActivityViewModel.getPokemons()?.value!!)

        val listView = findViewById<ListView>(R.id.main_listview)
        val inputSearch = findViewById<EditText>(R.id.editText)

        // filtering
        inputSearch.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pokemonAdapter.filter.filter(s)
            }
        })

        listView.adapter = pokemonAdapter
        Handler().postDelayed(
            {
                pokemonAdapter.filter.filter("")
                pokemonAdapter.notifyDataSetChanged()
            },
            1500
        )
    }

    override fun onResume() {
        super.onResume()
        pokemonAdapter.notifyDataSetChanged()
    }
}
