package org.igorsmotto.kariba.entities

data class Game(
  val deck: ShuffledDeck,
  val players: List<Player>,
  val lake: Lake
) {
  init {
    require(players.size >= 2)
  }

  fun hasFinished(): Boolean {
    return !(deck.size > 0 || players.any { it.hand.isNotEmpty() })
  }

  fun retrieveWinner(): Player? {
    return if (hasFinished()) {
      players.maxByOrNull { it.points }
    } else null
  }

  fun nextPlayerToPlay(currentPlayerPositionOnTable: Int): Player {
    if (hasFinished()) {
      throw IllegalStateException("A finished game doesn't have a next player")
    }

    val player = players.first { it.positionOnTable == (currentPlayerPositionOnTable + 1) % players.size }

    return if (player.hand.isEmpty()) nextPlayerToPlay(player.positionOnTable)
    else player
  }

  fun firstPlayer(): Player {
    return players.first { it.positionOnTable == 0 }
  }

  fun nextGame(play: Play): Game {
    val cardsPlayed = List(play.times) { play.card }

    val (updatedLake, removedCards) = lake.placeCards(cardsPlayed)
    val (cardsDealt, updatedDeck) = deck.deal(play.times)

    val updatedPlayer = play.player.copy(
      points = play.player.points + removedCards, //TODO("Do test")
      hand = play.player.hand - cardsPlayed + cardsDealt
    )

    return Game(
      deck = updatedDeck,
      players = players - play.player + updatedPlayer,
      lake = updatedLake
    )
  }
}

