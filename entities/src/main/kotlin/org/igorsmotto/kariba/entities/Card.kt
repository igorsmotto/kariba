package org.igorsmotto.kariba.entities

sealed class Card(
    val name: String,
    val value: Int
) : Comparable<Card> {
    object Rat : Card("rat", 1)
    object Weasel : Card("weasel", 2)
    object Zebra : Card("zebra", 3)
    object Giraffe : Card("giraffe", 4)
    object Ostrich : Card("ostrich", 5)
    object Jaguar : Card("jaguar", 6)
    object Rhino : Card("rhino", 7)
    object Elephant : Card("elephant", 8)

    override operator fun compareTo(other: Card): Int {
        if (this == Rat && other == Elephant) return 1
        if (this == Elephant && other == Rat) return -1

        return when {
            this.value == other.value -> 0
            this.value < other.value -> -1
            else -> 1
        }
    }

    companion object {
        fun values(): List<Card> {
            return Card::class.sealedSubclasses.map { it.objectInstance as Card }
        }
    }
}