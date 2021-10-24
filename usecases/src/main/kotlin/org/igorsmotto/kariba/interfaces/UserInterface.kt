package org.igorsmotto.kariba.interfaces

import org.igorsmotto.kariba.entities.Card
import org.igorsmotto.kariba.entities.Play
import org.igorsmotto.kariba.entities.Player
import org.igorsmotto.kariba.models.GameState
import org.igorsmotto.kariba.models.PlayerState
import org.igorsmotto.kariba.models.toPlayerState

abstract class UserInterface {
  abstract fun printState(gameState: GameState)
  abstract fun promptUserForQuantity(card: String, maxQuantity: Int): Int
  abstract fun promptUserForCard(playerState: PlayerState): String

  fun promptPlay(player: Player, validatePlay: (Play) -> Boolean): Play {
    val cardSelected = promptUserForCard(player.toPlayerState()).let { Card.valueOf(it.uppercase()) }
    val maxQuantity = player.repeatedCard(cardSelected)
    val times = if (maxQuantity > 1) {
      promptUserForQuantity(cardSelected.toString(), maxQuantity)
    } else 1

    val play = Play(cardSelected, times, player)
    return if (validatePlay(play)) play
    else promptPlay(player, validatePlay)
  }


}