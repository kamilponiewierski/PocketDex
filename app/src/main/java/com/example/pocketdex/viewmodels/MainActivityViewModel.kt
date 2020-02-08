package com.example.pocketdex.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pocketdex.dto.*

import androidx.lifecycle.ViewModel
import com.example.pocketdex.repositories.PokemonRepository

class MainActivityViewModel : ViewModel() {
    private var mPokemons : MutableLiveData<List<PokemonFullDTO>>? = null
    private lateinit var mRepo : PokemonRepository
//    private var mIsUpdating : MutableLiveData<Boolean> = MutableLiveData()

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

    fun getPokemons() : LiveData<List<PokemonFullDTO>>? {return mPokemons}

    //TODO loading bar
//    fun addNewValue(pokemon : PokemonFullDTO) {
//        mIsUpdating.value = true
//
//        mIsUpdating.value = false
//    }

//    fun getIsUpdating() : LiveData<Boolean>
//    {
//
//    }
}