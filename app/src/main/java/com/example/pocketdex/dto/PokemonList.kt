package com.example.pocketdex.dto

data class PokemonList(val count : Int,
                       val next: String, val previous: String,
                       val results: List<PokemonDTO>)