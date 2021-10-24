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
    if (deck.size > 0) return false
    if (players.any { it.hand.isNotEmpty() }) return false
    return true
  }

  fun retrieveWinner(): Player? {
    return if (hasFinished()) {
      players.maxByOrNull { it.points }
    } else null
  }

  fun nextPlayerToPlay(currentPlayerPositionOnTable: Int): Player {
    return players.first { it.positionOnTable == (currentPlayerPositionOnTable + 1) % players.size }
  }
}

data class Player(
  val name: String,
  val hand: Hand,
  val points: Int = 0,
  val positionOnTable: Int
)