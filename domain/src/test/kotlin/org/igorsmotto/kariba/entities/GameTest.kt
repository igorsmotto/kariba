package org.igorsmotto.kariba.entities

import org.igorsmotto.kariba.entities.Fixture.aPlayer
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.doesNotContain
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNotEqualTo
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

  @Test
  fun `A finished game shouldn't have a next player`() {
    val aFinishedGame = Game(
      Deck(0).shuffle(), listOf(
        aPlayer().copy(hand = emptyList(), positionOnTable = 0),
        aPlayer().copy(hand = emptyList(), positionOnTable = 1)
      ),
      Lake()
    )


    assertThrows<IllegalStateException> {
      aFinishedGame.nextPlayerToPlay(0)
    }
  }

  @Test
  fun `If the next player to play doesn't have cards it should search for the next one`() {
    val firstPlayer = aPlayer().copy(positionOnTable = 0, hand = emptyList())
    val secondPlayer = aPlayer().copy(positionOnTable = 1, hand = emptyList())
    val thirdPlayer = aPlayer().copy(positionOnTable = 2)
    val aGame = Game(
      Deck(30).shuffle(), listOf(
        firstPlayer,
        secondPlayer,
        thirdPlayer
      ),
      Lake()
    )

    val nextPlayer = aGame.nextPlayerToPlay(thirdPlayer.positionOnTable)

    expectThat(nextPlayer).isEqualTo(thirdPlayer)
  }

  @Test
  fun `The first player of a game is the player with position 0`() {
    val firstPlayer = aPlayer().copy(positionOnTable = 0)
    val lastPlayer = aPlayer().copy(positionOnTable = 1)
    val aGame = Game(
      Deck(30).shuffle(), listOf(
        firstPlayer,
        lastPlayer
      ),
      Lake()
    )

    expectThat(aGame.firstPlayer()).isEqualTo(firstPlayer)
  }

  @Test
  fun `When a play is made, it should be placed on the lake and a new card dealt`() {
    val firstPlayer = aPlayer().copy(
      positionOnTable = 0,
      hand = listOf(Card.WEASEL, Card.WEASEL, Card.RHINO, Card.ELEPHANT)
    )
    val lastPlayer = aPlayer().copy(positionOnTable = 1)
    val aGame = Game(
      Deck(30).shuffle(), listOf(
        firstPlayer,
        lastPlayer
      ),
      Lake()
    )
    val aPlay = Play(Card.WEASEL, 2, firstPlayer)

    val result = aGame.nextGame(aPlay)

    expectThat(result) {
      get { deck.size }.isEqualTo(28)
      get { lake.water[Card.WEASEL.value]!!.size }.isNotEqualTo(0)
      get { result.players.first { it.name == firstPlayer.name }.hand }
        .and {
          get { size }.isEqualTo(4)
          contains(Card.RHINO, Card.ELEPHANT)
          doesNotContain(Card.WEASEL, Card.WEASEL)
        }
    }
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