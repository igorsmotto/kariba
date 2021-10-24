package org.igorsmotto.kariba.entities

import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.test.Test

class LakeTest {

  @Test
  fun `a lake should have a position for all animals`() {
    assertThrows<IllegalArgumentException> {
      Lake(water = emptyMap())
    }
  }

  @Test
  fun `all cards positioned on a lake should match its place`() {
    val aLake = Lake()

    assertThrows<IllegalArgumentException> {
      aLake.copy(water = aLake.water + (Card.RAT.value to listOf(Card.WEASEL)))
    }
  }

  @Test
  fun `when placing a card on an empty lake it should place normally`() {
    val anEmptyLake = Lake()
    val aCardToAdd = Card.ZEBRA

    val (newLake, _) = anEmptyLake.placeCards(listOf(aCardToAdd))

    expectThat(newLake.water[Card.ZEBRA.value]!!.size).isEqualTo(1)
  }

  @Test
  fun `when placing 3 rats it should remove all elephants`() {
    val aLakeWithBunchElephants =
      Lake().let { empty ->
        empty.copy(water = empty.water + (Card.ELEPHANT.value to List(5) { Card.ELEPHANT }))
      }
    val ratsToAdd = List(3) { Card.RAT }

    val (newLake, cardsRemoved) = aLakeWithBunchElephants.placeCards(ratsToAdd)

    expectThat(cardsRemoved).isEqualTo(5)
    expectThat(newLake.water[Card.RAT.value]!!.size).isEqualTo(3)
    expectThat(newLake.water[Card.ELEPHANT.value]!!.size).isEqualTo(0)
  }

  @Test
  fun `when placing 3 animals of a kind it should remove the next placed animal`() {
    val aLakeWithRhinos =
      Lake().let { empty ->
        empty.copy(water = empty.water + (Card.RHINO.value to List(5) { Card.RHINO }))
      }
    val elephantsToAdd = List(3) { Card.ELEPHANT }

    val (newLake, cardsRemoved) = aLakeWithRhinos.placeCards(elephantsToAdd)

    expectThat(cardsRemoved).isEqualTo(5)
    expectThat(newLake.water[Card.ELEPHANT.value]!!.size).isEqualTo(3)
    expectThat(newLake.water[Card.RHINO.value]!!.size).isEqualTo(0)
  }

  @Test
  fun `by placing a fourth animal of a kind it should remove the next placed animal`() {
    val aLakeWithRhinosAndZebras =
      Lake().let { empty ->
        empty.copy(
          water = empty.water
                  + (Card.RHINO.value to List(5) { Card.RHINO })
                  + (Card.ZEBRA.value to List(6) { Card.ZEBRA })
        )
      }
    val elephantsToAdd = List(3) { Card.ELEPHANT }

    val (updatedLake, rhinosRemoved) = aLakeWithRhinosAndZebras.placeCards(elephantsToAdd)
    val (newLake, zebrasRemoved) = updatedLake.placeCards(listOf(Card.ELEPHANT))

    expectThat(rhinosRemoved).isEqualTo(5)
    expectThat(zebrasRemoved).isEqualTo(6)
    expectThat(newLake.water[Card.ELEPHANT.value]!!.size).isEqualTo(4)
    expectThat(newLake.water[Card.RHINO.value]!!.size).isEqualTo(0)
    expectThat(newLake.water[Card.ZEBRA.value]!!.size).isEqualTo(0)
  }

  @Test
  fun `no animal should be removed if there is no animal next in line`() {
    val aLakeWithBunchElephants =
      Lake().let { empty ->
        empty.copy(water = empty.water + (Card.ELEPHANT.value to List(5) { Card.ELEPHANT }))
      }
    val cardsToAdd = List(3) { Card.ZEBRA }

    val (newLake, cardsRemoved) = aLakeWithBunchElephants.placeCards(cardsToAdd)

    expectThat(cardsRemoved).isEqualTo(0)
    expectThat(newLake.water[Card.ZEBRA.value]!!.size).isEqualTo(3)
    expectThat(newLake.water[Card.ELEPHANT.value]!!.size).isEqualTo(5)
  }
}