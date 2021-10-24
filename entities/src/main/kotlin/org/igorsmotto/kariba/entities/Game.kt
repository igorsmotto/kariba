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

    fun placeCard(position: Int, newCards: List<Card>): Pair<Lake, Int> {
        require(position in (1..8))

        val updatedList = water[position]!!.plus(newCards)
        val updatedLake =
            this.copy(water = mapOf(position to updatedList) + water.filterKeys { key -> key != position })

        return if (updatedList.size >= 3) {
            val (clearPosition, clearedCards) = findPositionToClear(position)
            updatedLake.copy(water = mapOf(clearPosition to emptyList<Card>()) + updatedLake.water.filterKeys { key -> key != clearPosition }) to clearedCards
        } else {
            updatedLake to 0
        }
    }

    private fun findPositionToClear(position: Int): Pair<Int, Int> {
        return if (position == 1 && this.water[8]!!.isNotEmpty()) 8 to this.water[8]!!.size
        else findNextNonEmptyPosition(position - 1)
    }

    private fun findNextNonEmptyPosition(position: Int): Pair<Int, Int> {
        if (this.water[position]!!.isNotEmpty()) return position to this.water[position]!!.size
        if (position == 1) return 1 to 0
        return findNextNonEmptyPosition(position - 1)
    }
}