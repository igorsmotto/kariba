package org.igorsmotto.kariba.models

import org.igorsmotto.kariba.entities.Card
import org.igorsmotto.kariba.entities.Game
import org.igorsmotto.kariba.entities.Lake
import org.igorsmotto.kariba.entities.Player

data class FinalState(
  val winner: PlayerState,
  val gameState: GameState
)

data class GameState(
  val numberOfRemainingCards: Int,
  val players: List<PlayerState>,
  val lake: LakeState
)

fun Game.toGameState() = GameState(
  deck.size,
  players.map { it.toPlayerState() },
  lake.toLakeState()
)

data class LakeState(
  val water: Map<Int, List<Card>>
)

fun Lake.toLakeState() = LakeState(water)


data class PlayerState(
  val name: String,
  val hand: List<String>,
  val points: Int = 0
)

fun Player.toPlayerState() = PlayerState(name, hand.map { it.toString() }, points)