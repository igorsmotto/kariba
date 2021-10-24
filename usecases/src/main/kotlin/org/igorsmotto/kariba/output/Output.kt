package org.igorsmotto.kariba.output

import org.igorsmotto.kariba.entities.Card
import org.igorsmotto.kariba.entities.Hand
import org.igorsmotto.kariba.entities.Lake
import org.igorsmotto.kariba.entities.Player

data class GameState(
    val numberOfRemainingCards: Int,
    val players: List<PlayerState>,
    val lake: LakeState
)

data class LakeState(
    val water: Map<Int, List<Card>>
)

data class PlayerState(
    val name: String,
    val hand: Hand,
    val points: Int = 0
)

fun Lake.toLakeState() = LakeState(water)
fun Player.toPlayerState() = PlayerState(name, hand, points)