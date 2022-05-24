package com.github.maksban.noteapp.data.model


import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("fact")
    val fact: String
){
    override fun toString(): String {
        return fact ?: ""
    }
}
