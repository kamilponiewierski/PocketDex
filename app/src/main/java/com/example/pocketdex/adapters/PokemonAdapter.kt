package com.example.pocketdex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pocketdex.R
import com.example.pocketdex.dto.PokemonFullDTO
import java.util.*

class PokemonAdapter(context : Context, pokemons : List<PokemonFullDTO>) : BaseAdapter(), Filterable
{
    private val mContext : Context = context
    private val mOriginalPokemonData : List<PokemonFullDTO> = pokemons
    private var mFilteredPokemonData : List<PokemonFullDTO> = pokemons

    @ExperimentalStdlibApi
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val row = layoutInflater.inflate(R.layout.row_main, parent, false)

        val nameTextView = row.findViewById<TextView>(R.id.name)
        nameTextView.text = mFilteredPokemonData[position].name.capitalize(Locale.ENGLISH)

        val sprite = row.findViewById<ImageView>(R.id.pokemonSprite)
        if (convertView != null) {
            Glide
                .with(convertView)
                .load(mFilteredPokemonData[position].sprites["front_default"])
                .apply(RequestOptions().placeholder(R.drawable.pokeball_placeholder).fitCenter())
                .into(sprite)
        }

        val positionTextView = row.findViewById<TextView>(R.id.url)
        positionTextView.text = mContext.getString(R.string.pokedex_number).plus(mFilteredPokemonData[position].id)

        return row
    }

    override fun getItem(position: Int): Any {
        return mFilteredPokemonData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mFilteredPokemonData.size
    }

    override fun getFilter(): Filter {
        return object : Filter()
        {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString : String = constraint.toString().toLowerCase(Locale.ROOT)
                val filterResults = FilterResults()

                filterResults.values = if (queryString.isEmpty())
                    mOriginalPokemonData
                else
                    mOriginalPokemonData.filter { it.name.startsWith(queryString) }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mFilteredPokemonData = results?.values as List<PokemonFullDTO>
                mFilteredPokemonData = mFilteredPokemonData.sortedBy { it.id }
                notifyDataSetChanged()
            }
        }
    }
}