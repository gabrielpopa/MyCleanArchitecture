package com.gabrielpopa.myapplication.domain.login.entity

import com.gabrielpopa.myapplication.domain.common.Entity

data class LoginEntity(
    override val id : Int,
    override val name: String,
    override val email: String,
    override val token: String
) : Entity()