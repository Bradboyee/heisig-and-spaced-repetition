package com.example.finalproject


sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Failure(val message : String) : Resource<Nothing>()
}