package br.com.montecardo.simplistic.di

import javax.inject.Scope

@Scope
@Retention
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class FragmentScoped