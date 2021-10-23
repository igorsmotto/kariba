package org.igorsmotto.kariba.entities

import kotlin.math.max

@JvmInline
value class Hand(private val cards: List<Card>) {
    init {
        require(cards.size == 5)
    }
}

private const val MAX_CARDS = 64
class Deck {
    private val cards = (1..MAX_CARDS / Card.values().size).map { Card.values().toList() }.flatten()

    fun shuffle(): ShuffledDeck = ShuffledDeck(cards.shuffled())
}

@JvmInline
value class ShuffledDeck(val cards: List<Card>)

fun deal(shuffledDeck: ShuffledDeck, numberOfCards: Int): Pair<List<Card>, ShuffledDeck> {
    val cards = shuffledDeck.cards
    return cards.takeLast(numberOfCards) to ShuffledDeck(cards = cards.take(max(cards.size - numberOfCards, 0)))
}

fun dealHand(shuffledDeck: ShuffledDeck): Pair<Hand, ShuffledDeck> {
    val (dealtCards, remainingDeck) = deal(shuffledDeck, 5)
    return Hand(dealtCards) to remainingDeck
}