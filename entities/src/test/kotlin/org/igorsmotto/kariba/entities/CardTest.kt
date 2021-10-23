package org.igorsmotto.kariba.entities

import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo
import strikt.assertions.isGreaterThan
import strikt.assertions.isLessThan
import kotlin.test.Test

class CardTest {

    @Test
    fun `when comparing cards, the rat should be lesser than the weasel and so forth`() {
        expectThat<Card>(Card.Rat).isLessThan(Card.Weasel)
        expectThat<Card>(Card.Weasel).isLessThan(Card.Zebra)
        expectThat<Card>(Card.Zebra).isLessThan(Card.Giraffe)
        expectThat<Card>(Card.Giraffe).isLessThan(Card.Ostrich)
        expectThat<Card>(Card.Ostrich).isLessThan(Card.Jaguar)
        expectThat<Card>(Card.Jaguar).isLessThan(Card.Rhino)
        expectThat<Card>(Card.Rhino).isLessThan(Card.Elephant)
        expectThat<Card>(Card.Rat).isEqualTo(Card.Rat)
    }

    @Test
    fun `when comparing the rat and the elephant, rat should be bigger`() {
        Card.values().filter { it !in listOf(Card.Rat, Card.Elephant) }.forEach {
            expectThat<Card>(Card.Rat).isLessThan(it)
            expectThat<Card>(Card.Elephant).isGreaterThan(it)
        }

        expectThat<Card>(Card.Rat).isGreaterThan(Card.Elephant)
        expectThat<Card>(Card.Elephant).isLessThan(Card.Rat)
    }

    @Test
    fun `when using values function, it should return exactly all cards`() {
        expectThat(Card.values())
            .containsExactly(
                Card.Rat,
                Card.Weasel,
                Card.Zebra,
                Card.Giraffe,
                Card.Ostrich,
                Card.Jaguar,
                Card.Rhino,
                Card.Elephant
            )
    }
}