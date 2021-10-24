package org.igorsmotto.kariba.input

@JvmInline
value class PlayerName(val name: String)

data class Play(
    val card: String,
    val quantity: Int = 1
)