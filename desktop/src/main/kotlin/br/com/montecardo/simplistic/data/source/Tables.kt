package br.com.montecardo.simplistic.data.source

import kdbc.Table

object NodeTable : Table("node") {
    val id = column<Long>("id")
    val description = column<String>("description")
    val parentId = column<Long>("parent_id")
}

object VersionTable : Table("version") {
    val key = column<Boolean>("key")
    val version = column<String>("version")
}