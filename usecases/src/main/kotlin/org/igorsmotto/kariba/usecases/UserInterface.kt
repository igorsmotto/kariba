package org.igorsmotto.kariba.usecases

import org.igorsmotto.kariba.input.PlayerName
import org.igorsmotto.kariba.output.GameState

interface UserInterface {
    fun promptPlay(player: PlayerName, hand: List<String>): Play
    fun printState(gameState: GameState)
}

data class Play(
    val card: String,
    val quantity: Int = 1
)