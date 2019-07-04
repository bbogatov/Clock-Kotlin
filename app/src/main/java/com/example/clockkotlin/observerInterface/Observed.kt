package com.example.clockkotlin.observerInterface

interface Observed {

    fun addObserver(observer: Observer)

    fun removeObserver(observer: Observer)

    fun notifyObservers()
}
