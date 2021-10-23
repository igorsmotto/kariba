package org.igorsmotto.kariba.entities

data class Player(
    val name: String,
    val hand: Hand,
    val points: Int = 0,
    val order: Int
)

data class Lake(
    val water: Map<Int, List<Card>> = mapOf(
        1 to emptyList(),
        2 to emptyList(),
        3 to emptyList(),
        4 to emptyList(),
        5 to emptyList(),
        6 to emptyList(),
        7 to emptyList(),
        8 to emptyList()
    )
) {
    init {
        require(this.water.all { it.value.all { card -> card.value == it.key } })
        require(this.water.keys.containsAll((1..8).toList()))
    }

    fun addCard(position: Int, newCards: List<Card>): Lake {
        require(position in (1..8))

        val updatedList = water[position]!!.plus(newCards)

        return this.copy(water = mapOf(position to updatedList) + water.filterKeys { key -> key != position })
    }
}