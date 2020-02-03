package com.example.pokemon_api_example.dto

import com.google.gson.annotations.SerializedName

data class PokemonDTO(@SerializedName("name")val pokemonName: String, val url: String) {
}