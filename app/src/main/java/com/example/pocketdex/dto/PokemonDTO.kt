package com.example.pocketdex.dto

import com.google.gson.annotations.SerializedName

data class PokemonDTO(@SerializedName("name")val pokemonName: String, val url: String)