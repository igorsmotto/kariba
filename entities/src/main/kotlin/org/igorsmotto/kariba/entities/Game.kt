package org.igorsmotto.kariba.entities

data class Game(
    val deck: ShuffledDeck,
    val players: List<Player>,
    val lake: Lake
)

data class Player(
    val name: String,
    val hand: Hand,
    val points: Int = 0
)

data class Lake(
    val water: Map<Int, List<Card>>
) {
    init {
        require(this.water.all { it.value.all { card -> card.value == it.key } })
        require(this.water.keys.containsAll((1..8).toList()))
    }
}