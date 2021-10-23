package org.igorsmotto.kariba.entities

import kotlin.math.max

@JvmInline
value class Hand(val cards: List<Card>) {
    init {
        require(cards.size <= 5)
    }
}

private const val MAX_CARDS = 12

object Deck {
    private val cards = List(MAX_CARDS / Card.values().size + 1) { Card.values().toList() }.flatten().take(MAX_CARDS)

    fun shuffle(): ShuffledDeck = ShuffledDeck(cards.shuffled())
}

data class ShuffledDeck(val cards: List<Card>) {
    val size = cards.size
}

fun deal(shuffledDeck: ShuffledDeck, numberOfCards: Int): Pair<List<Card>, ShuffledDeck> {
    val cards = shuffledDeck.cards
    return cards.takeLast(numberOfCards) to ShuffledDeck(cards = cards.take(max(cards.size - numberOfCards, 0)))
}

fun dealHand(shuffledDeck: ShuffledDeck): Pair<Hand, ShuffledDeck> {
    val (dealtCards, remainingDeck) = deal(shuffledDeck, 5)
    return Hand(dealtCards) to remainingDeck
}