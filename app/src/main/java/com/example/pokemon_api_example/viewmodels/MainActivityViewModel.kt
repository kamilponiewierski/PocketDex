package com.example.pokemon_api_example.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemon_api_example.dto.*

import androidx.lifecycle.ViewModel
import com.example.pokemon_api_example.repositories.PokemonRepository

class MainActivityViewModel : ViewModel() {
    private var mPokemons : MutableLiveData<List<PokemonFullDTO>>? = null
    private lateinit var mRepo : PokemonRepository
    private var mIsUpdating : MutableLiveData<Boolean> = MutableLiveData()

    fun init()
    {
        if(mPokemons != null)
            return
        else
        {
            mRepo = PokemonRepository.instance
            mPokemons = mRepo.getPokemons()
        }
    }

    //TODO loading bar
//    fun addNewValue(pokemon : PokemonFullDTO) {
//        mIsUpdating.value = true
//
//        mIsUpdating.value = false
//    }

    fun getPokemons() : MutableLiveData<List<PokemonFullDTO>>? {return mPokemons}

    fun getPokemonList() : List<PokemonFullDTO> {
        return mRepo.getPokemonList()
    }

    fun getMatchedPokemon(query : String) : List<PokemonFullDTO>
    {
        val regexPattern = Regex("$query.*")
        val queryMatches = mutableListOf<PokemonFullDTO>()
        for(poke in mRepo.getPokemonList())
        {
            if (poke.name.matches(regex = regexPattern))
            {
                queryMatches.add(poke)
            }

        }
        return queryMatches
    }

//    fun getIsUpdating() : LiveData<Boolean>
//    {
//
//    }
}