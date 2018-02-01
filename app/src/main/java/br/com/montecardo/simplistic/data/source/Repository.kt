package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Item

interface Repository {
    fun getRootItem(): List<Item>

    fun getSubItems(listing: Item.Listing): List<Item>?
}