package org.igorsmotto.kariba.entities

data class Player(
  val name: String,
  val hand: Hand,
  val positionOnTable: Int,
  val points: Int = 0
) {
  fun repeatedCard(card: Card): Int {
    return hand.count { it == card }
  }
}

data class Play(
  val card: Card,
  val times: Int = 1,
  val player: Player
) {
  fun isValid(): Boolean {
    return player.hand.count { it == card } >= times
  }
}