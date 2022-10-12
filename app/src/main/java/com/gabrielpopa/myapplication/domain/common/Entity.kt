package com.gabrielpopa.myapplication.domain.common

abstract class Entity {
    abstract val id: Int
    abstract val name: String
    abstract val email: String
    abstract val token: String
}