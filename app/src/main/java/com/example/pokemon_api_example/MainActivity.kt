package com.example.pokemon_api_example

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
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

        // setting up empty view
        val emptyView = findViewById<ImageView>(R.id.empty_query)
        val emptyQueryImage = resources.getDrawable(R.drawable.empty_list_image,null)
        emptyView.setImageDrawable(emptyQueryImage)
        listView.emptyView = emptyView

        // searchbar
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
                pokemonAdapter.notifyDataSetChanged()
            }
        })

        listView.adapter = pokemonAdapter

        // auto-refresh view
        val mRunnable : Runnable = object
            : Runnable {
            override fun run() {
                if (!inputSearch.isFocused)
                    pokemonAdapter.filter.filter("")
                pokemonAdapter.notifyDataSetChanged()
                Log.d("viewUpdate", "view updated by runnable")
                listView.postDelayed(this, 1000)
            }
        }
        listView.postDelayed(mRunnable, 1000)
    }

    override fun onResume() {
        super.onResume()
        pokemonAdapter.notifyDataSetChanged()
    }
}
