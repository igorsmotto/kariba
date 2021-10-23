package org.igorsmotto.kariba.cli

import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.output.TermUi
import org.igorsmotto.kariba.input.PlayerName
import org.igorsmotto.kariba.output.GameState
import org.igorsmotto.kariba.usecases.Play
import org.igorsmotto.kariba.usecases.UserInterface


object CLIInterface : UserInterface {
    override fun promptPlay(player: PlayerName, hand: List<String>): Play {
        val cardSelected =
            TermUi.prompt("[${player.name}] -> Select a card from hand [${hand.joinToString()}] to play")
                ?: throw UsageError("A play is obligatory")

        val repeatedCards = hand.count { it == cardSelected }
        val quantity = if (repeatedCards > 1) {
            TermUi.prompt("How many cards do you wanna play?", default = "1") {
                it.toIntOrNull() ?: throw UsageError("$it is not a valid integer")
            } ?: 1
        } else {
            1
        }

        return Play(cardSelected, quantity)
    }

    override fun printState(gameState: GameState) {
        TODO("Not yet implemented")
    }

}

