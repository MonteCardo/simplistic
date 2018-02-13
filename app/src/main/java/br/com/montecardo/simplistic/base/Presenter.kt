package br.com.montecardo.simplistic.base

interface Presenter<in V: View> {
    fun onAttach(view: V)

    fun onDetach()
}