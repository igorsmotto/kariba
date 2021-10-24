package org.igorsmotto.kariba.entities

import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import kotlin.test.Ignore
import kotlin.test.Test

class DeckTest {

    @Test
    fun `when retrieving a deck, it should contain all animals in the same proportion`() {
        val cards = Deck(64).shuffle().cards

        val countByAnimal = cards.groupingBy { it }.eachCount().values.toSet()
        expectThat(countByAnimal)
            .hasSize(1)
    }

    @Test
    @Ignore // Since this test random behavior, it will be a flaky test
    fun `when shuffling a deck, it should shuffle randomly`() {
        val deck = Deck(64)

        val shuffledDeck1 = deck.shuffle().cards
        val shuffledDeck2 = deck.shuffle().cards
        expectThat(shuffledDeck1.first()).isNotEqualTo(shuffledDeck2.first())
    }

    @Test
    fun `when dealing a card, it should be removed from the deck`() {
        val deck = Deck(64).shuffle()

        val (card, remainingDeck) = deck.deal(1)

        val countOfUnDealtDeck = deck.cards.groupingBy { it }.eachCount()[card.first()]!!
        val countOfDealtDeck = remainingDeck.cards.groupingBy { it }.eachCount()[card.first()]!!
        expectThat(countOfDealtDeck).isEqualTo(countOfUnDealtDeck - 1)
    }

    @Test
    fun `when dealing all cards, the deck should be emptied`() {
        val deck = Deck(12).shuffle()

        val (_, remainingDeck) = deck.deal(12)

        expectThat(remainingDeck.cards.size).isEqualTo(0)
    }

    @Test
    fun `when dealing a cards with the deck empty, it should not deal the card`() {
        val emptyDeck = Deck(0).shuffle()

        val (dealtCards, _) = emptyDeck.deal(1)

        expectThat(dealtCards.size).isEqualTo(0)
    }
}