package br.com.montecardo.simplistic.base

interface PersistentPresenter<in V : View, in S : State> {
    fun onAttach(view: V, state: S)

    fun onDetach()
}

interface Presenter<in V : View> {
    fun onAttach(view: V)

    fun onDetach()
}

interface State

interface View