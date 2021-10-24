package org.igorsmotto.kariba.entities

import org.igorsmotto.kariba.entities.Fixture.aPlayer
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import java.util.UUID
import kotlin.test.Test

class GameTest {

  @Test
  fun `a game must have at least 2 players`() {
    assertThrows<IllegalArgumentException> {
      Game(Deck(64).shuffle(), emptyList(), Lake())
    }
  }

  @Test
  fun `a game that have some cards left on table is not finished`() {
    val aGameWithCardsLeft = Game(Deck(5).shuffle(), listOf(aPlayer(), aPlayer()), Lake())

    expectThat(aGameWithCardsLeft.hasFinished()).isFalse()
  }

  @Test
  fun `a game that have no cards left on table, but hands are not empty, is not finished`() {
    val aGameWithoutCardsButNotFinished = Game(
      Deck(0).shuffle(), listOf(
        aPlayer(),
        aPlayer().copy(hand = emptyList())
      ),
      Lake()
    )

    expectThat(aGameWithoutCardsButNotFinished.hasFinished()).isFalse()
  }

  @Test
  fun `a game is finished when all players have no cards and no cards are left on table`() {
    val aFinishedGame = Game(
      Deck(0).shuffle(), listOf(
        aPlayer().copy(hand = emptyList()),
        aPlayer().copy(hand = emptyList())
      ),
      Lake()
    )

    expectThat(aFinishedGame.hasFinished()).isTrue()
  }

  @Test
  fun `a game finished should have a winner (the player with most points)`() {
    val expectedWinnerPlayer = aPlayer().copy(hand = emptyList(), points = 3)
    val aFinishedGame = Game(
      Deck(0).shuffle(), listOf(
        expectedWinnerPlayer,
        aPlayer().copy(hand = emptyList(), points = 2)
      ),
      Lake()
    )

    expectThat(aFinishedGame.retrieveWinner()).isEqualTo(expectedWinnerPlayer)
  }

  @Test
  fun `After player in position 0 plays, the next player should be the position 1`() {
    val firstPlayer = aPlayer().copy(positionOnTable = 0)
    val secondPlayer = aPlayer().copy(positionOnTable = 1)
    val aGame = Game(
      Deck(30).shuffle(), listOf(
        firstPlayer,
        secondPlayer
      ),
      Lake()
    )

    val nextPlayer = aGame.nextPlayerToPlay(firstPlayer.positionOnTable)

    expectThat(nextPlayer).isEqualTo(secondPlayer)
  }

  @Test
  fun `After the last player plays, the next player should be in position 0`() {
    val firstPlayer = aPlayer().copy(positionOnTable = 0)
    val lastPlayer = aPlayer().copy(positionOnTable = 1)
    val aGame = Game(
      Deck(30).shuffle(), listOf(
        firstPlayer,
        lastPlayer
      ),
      Lake()
    )

    val nextPlayer = aGame.nextPlayerToPlay(lastPlayer.positionOnTable)

    expectThat(nextPlayer).isEqualTo(firstPlayer)
  }
}

object Fixture {
  fun aPlayer() =
    Player(
      name = UUID.randomUUID().toString(),
      hand = listOf(Card.ZEBRA, Card.ELEPHANT, Card.ZEBRA, Card.RAT, Card.WEASEL),
      points = 0,
      positionOnTable = 1
    )
}