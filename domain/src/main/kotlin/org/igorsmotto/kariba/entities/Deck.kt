package org.igorsmotto.kariba.entities

import kotlin.math.max

typealias Hand = List<Card>
const val HAND_SIZE = 5

class Deck(deckSize: Int = 64) {
  private val cards =
    List(deckSize / Card.values().size + 1) { Card.values().toList() }
      .flatten()
      .take(deckSize)

  fun shuffle(): ShuffledDeck =
    ShuffledDeck(cards.shuffled())
}

data class ShuffledDeck(val cards: List<Card>) {
  val size = cards.size

  fun deal(numberOfCards: Int): Pair<List<Card>, ShuffledDeck> {
    return cards.takeLast(numberOfCards) to ShuffledDeck(cards = cards.take(max(cards.size - numberOfCards, 0)))
  }

  fun dealHand(): Pair<Hand, ShuffledDeck> {
    return deal(HAND_SIZE)
  }
}
