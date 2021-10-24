package org.igorsmotto.kariba.entities

import strikt.api.expect
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import kotlin.test.Test

class PlayerTest {

  @Test
  fun `repeated card function should return the number of repeated of the same card`() {
    val aPlayer = Player(
      "A", listOf(Card.WEASEL, Card.WEASEL, Card.WEASEL, Card.ELEPHANT), 0
    )

    val weaselResult = aPlayer.repeatedCard(Card.WEASEL)
    val rhinoResult = aPlayer.repeatedCard(Card.RHINO)

    expect {
      that(weaselResult).isEqualTo(3)
      that(rhinoResult).isEqualTo(0)
    }
  }

  @Test
  fun `a play is considered valid if the number of cards is present on the players hand`() {
    val aPlayer = Player("A", listOf(Card.WEASEL, Card.WEASEL, Card.WEASEL, Card.ELEPHANT), 0)
    val aValidPlay = Play(Card.WEASEL, 2, aPlayer)
    val anInvalidPlay = Play(Card.WEASEL, 4, aPlayer)
    val anotherInvalidPlay = Play(Card.RHINO, 1, aPlayer)

    val validPlayResult = aValidPlay.isValid()
    val invalidPlayResult1 = anInvalidPlay.isValid()
    val invalidPlayResult2 = anotherInvalidPlay.isValid()

    expect {
      that(validPlayResult).isTrue()
      that(invalidPlayResult1).isFalse()
      that(invalidPlayResult2).isFalse()
    }
  }
}