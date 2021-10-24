package org.igorsmotto.kariba.cli

import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.output.TermUi.echo
import org.igorsmotto.kariba.input.PlayerName
import org.igorsmotto.kariba.output.GameState
import org.igorsmotto.kariba.output.LakeState
import org.igorsmotto.kariba.output.PlayerState
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
    echo(
      """
        |--------------------------------
        |
        |Players: 
        |${drawPlayers(gameState.players)}
        |
        |Lake:
        |${drawLake(gameState.lake)}
        |
        |Remaining Cards on Table: ${gameState.numberOfRemainingCards}
            |--------------------------------
        """.trimMargin()
    )
  }

  private fun drawLake(lakeState: LakeState): String {
    return """
        [                    ${lakeState.water[1]!!.size}
        [                ________
        [              /   rat    \
        [          ${lakeState.water[8]!!.size} / eleph weasel \ ${lakeState.water[2]!!.size}
        [          /                  \
        [         ]                    |
        [       ${lakeState.water[7]!!.size} | rhino        zebra | ${lakeState.water[3]!!.size}
        [         ]                    |
        [          \                  /
        [          ${lakeState.water[6]!!.size} \ jaguar giraf / ${lakeState.water[4]!!.size}
        [              \ __ostri__ /
        [                    ${lakeState.water[5]!!.size}
        """.trimMargin("[")
  }

  private fun drawPlayers(playerStates: List<PlayerState>): String {
    return playerStates.joinToString("\n") {
      "  - ${it.name}: ${it.points}"
    }
  }

}

