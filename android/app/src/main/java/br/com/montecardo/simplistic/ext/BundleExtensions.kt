package br.com.montecardo.simplistic.ext

import android.os.Bundle

fun Bundle?.getNullableLong(key: String): Long? {
    return this?.let {
        if (!it.containsKey(key)) null
        else it.getLong(key)
    }
}