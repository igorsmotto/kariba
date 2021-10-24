package org.igorsmotto.kariba.usecases

import org.igorsmotto.kariba.entities.Deck
import org.igorsmotto.kariba.entities.Game
import org.igorsmotto.kariba.entities.HAND_SIZE
import org.igorsmotto.kariba.entities.Lake
import org.igorsmotto.kariba.entities.Player
import org.igorsmotto.kariba.models.FinalState

class StartGame(
  private val mainGame: MainGame
) {
  fun execute(playerNames: List<String>, deckSize: Int): Result<FinalState> {
    validateGame(playerNames, deckSize)?.let {
      return Result.failure(it)
    }

    val shuffledDeck = Deck(deckSize).shuffle()
    val (players, deckOnTable) =
      playerNames
        .fold(emptyList<Player>() to shuffledDeck) { (players, deck), playerName ->
          val (handDealt, remainingDeck) = deck.dealHand()
          val player = Player(playerName, hand = handDealt, points = 0, positionOnTable = players.size)
          players.plus(player) to remainingDeck
        }

    val initialGame = Game(deckOnTable, players, Lake())
    return Result.success(mainGame.execute(initialGame))
  }

  private fun validateGame(playerNames: List<String>, deckSize: Int): Throwable? {
    if (playerNames.size < 2) {
      return InsufficientNumberOfPlayers
    }
    if (playerNames.size * HAND_SIZE > deckSize) {
      return InsufficientNumberOfCardsOnDeck
    }
    return null
  }
}

object InsufficientNumberOfPlayers : Throwable("There should be at least 2 players in order to play Kariba")
object InsufficientNumberOfCardsOnDeck : Throwable("Everyone should start with at least 5 cards")