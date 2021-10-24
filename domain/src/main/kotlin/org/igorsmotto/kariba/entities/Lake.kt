package org.igorsmotto.kariba.entities

data class Lake(
  val water: Map<Int, List<Card>> = (1..8).associateWith { emptyList() }
) {
  init {
    require(this.water.all { it.value.all { card -> card.value == it.key } })
    require(this.water.keys.containsAll((1..8).toList()))
  }

  fun placeCards(cards: List<Card>): Pair<Lake, Int> {
    val animalPositionOnLake = cards.first().value
    val updatedList = this.water[animalPositionOnLake]!!.plus(cards)

    val updatedLake =
      this.copy(water = mapOf(animalPositionOnLake to updatedList) + this.water.filterKeys { key -> key != animalPositionOnLake })

    return if (updatedList.size >= 3) {
      val (clearPosition, clearedCards) = findPositionToClear(animalPositionOnLake)
      updatedLake.copy(water = mapOf(clearPosition to emptyList<Card>()) + updatedLake.water.filterKeys { key -> key != clearPosition }) to clearedCards
    } else {
      updatedLake to 0
    }
  }

  private fun findPositionToClear(animalPositionOnLake: Int): Pair<Int, Int> {
    return if (animalPositionOnLake == 1 && water[8]!!.isNotEmpty()) 8 to water[8]!!.size
    else findNextNonEmptyPosition(animalPositionOnLake - 1)
  }

  private fun findNextNonEmptyPosition(animalPositionOnLake: Int): Pair<Int, Int> {
    if (this.water[animalPositionOnLake]!!.isNotEmpty()) return animalPositionOnLake to this.water[animalPositionOnLake]!!.size
    if (animalPositionOnLake == 1) return 1 to 0
    return findNextNonEmptyPosition(animalPositionOnLake - 1)
  }
}