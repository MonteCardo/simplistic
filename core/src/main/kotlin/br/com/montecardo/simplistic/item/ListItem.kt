package br.com.montecardo.simplistic.item

sealed class ListItem<T> {

    class Creator<T> : ListItem<T>()

    class Existent<T>(val item: T) : ListItem<T>()
}