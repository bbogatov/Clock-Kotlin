package com.example.clockkotlin.observerInterface

/**
 * This interface used with database, if some class need know when DB has any updates
 * That class must implement [Observer] interface, and do all logic in [Observer.handleEvent] method
 */
interface Observed {

    fun addObserver(observer: Observer)

    fun removeObserver(observer: Observer)

    fun notifyObservers()
}
