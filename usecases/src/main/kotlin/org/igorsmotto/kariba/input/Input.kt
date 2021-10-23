package org.igorsmotto.kariba.input

import org.igorsmotto.kariba.entities.Card

@JvmInline
value class PlayerName(val name: String)

fun String.toCard(): Card? {
    return Card.values().find {
        it.name == this
    }
}