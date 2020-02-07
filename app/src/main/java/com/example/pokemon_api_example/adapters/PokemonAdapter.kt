package com.example.pokemon_api_example.adapters

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.pokemon_api_example.R
import com.example.pokemon_api_example.dto.PokemonDTO
import com.example.pokemon_api_example.dto.PokemonFullDTO
import com.example.pokemon_api_example.viewmodels.MainActivityViewModel

class PokemonAdapter(context : Context, viewModel : MainActivityViewModel) : BaseAdapter()
{
    var pokemonList : List<PokemonFullDTO> = viewModel.getPokemonList()
    private val mContext : Context = context

    private val pokemon = pokemonList



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val row = layoutInflater.inflate(R.layout.row_main, parent, false)

        val nameTextView = row.findViewById<TextView>(R.id.name)
        nameTextView.text = pokemon[position].name.capitalize()

        val sprite = row.findViewById<ImageView>(R.id.pokemonSprite)
        if (convertView != null) {
            Glide
                .with(convertView)
                .load(pokemon[position].sprites.get("front_default"))
                .into(sprite)
            Log.i("sprite", "Loaded a sprite")
        }

        val positionTextView = row.findViewById<TextView>(R.id.url)
        positionTextView.text ="Pokedex # ".plus(pokemon[position].id)
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