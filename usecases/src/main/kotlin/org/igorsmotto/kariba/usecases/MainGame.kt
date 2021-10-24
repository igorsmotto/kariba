package org.igorsmotto.kariba.usecases

import org.igorsmotto.kariba.entities.Game
import org.igorsmotto.kariba.entities.Play
import org.igorsmotto.kariba.entities.Player
import org.igorsmotto.kariba.interfaces.UserInterface
import org.igorsmotto.kariba.models.FinalState
import org.igorsmotto.kariba.models.toGameState
import org.igorsmotto.kariba.models.toPlayerState

class MainGame(
  private val userInterface: UserInterface
) {
  fun execute(game: Game): FinalState {
    val player = game.firstPlayer()
    return loop(player, game)
  }

  private tailrec fun loop(player: Player, game: Game): FinalState {
    game.retrieveWinner()?.let { return FinalState(it.toPlayerState(), game.toGameState()) }

    userInterface.printState(game.toGameState())
    val play = userInterface.promptPlay(player, Play::isValid)

    val updatedGame = game.nextGame(play)
    val nextPlayer = game.nextPlayerToPlay(player.positionOnTable)

    return loop(nextPlayer, updatedGame)
  }
}