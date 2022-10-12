package com.gabrielpopa.myapplication.domain.second.entity

import com.gabrielpopa.myapplication.domain.common.Entity

data class SecondEntity(
    override val id : Int,
    override val name: String,
    override val email: String,
    override val token: String
) : Entity()