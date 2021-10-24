package org.igorsmotto.kariba.cli.interfaces

import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.output.TermUi.echo
import org.igorsmotto.kariba.interfaces.UserInterface
import org.igorsmotto.kariba.models.GameState
import org.igorsmotto.kariba.models.LakeState
import org.igorsmotto.kariba.models.PlayerState


object CLIInterface : UserInterface() {
  override fun promptUserForCard(playerState: PlayerState): String {
    return TermUi.prompt("[${playerState.name}] -> Select a card from hand [${playerState.hand.joinToString()}] to play")
      ?: throw UsageError("A play is obligatory")
  }

  override fun promptUserForQuantity(card: String, maxQuantity: Int): Int {
    return TermUi.prompt("How many ${card}s do you wanna play? [${(1..maxQuantity).joinToString()}]") {
      it.toIntOrNull() ?: throw UsageError("$it is not a valid integer")
    } ?: 1
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

