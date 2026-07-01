package ru.gmpopov.recipeapp.di

interface Factory<T> {
    fun create(): T
}