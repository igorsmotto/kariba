package org.igorsmotto.kariba.entities

enum class Card(
  val value: Int
) {
  RAT(1),
  WEASEL(2),
  ZEBRA(3),
  GIRAFFE(4),
  OSTRICH(5),
  JAGUAR(6),
  RHINO(7),
  ELEPHANT(8);

  override fun toString(): String {
    return super.toString().lowercase()
  }

  companion object {
    fun toCard(str: String): Card? {
      return runCatching { valueOf(str.uppercase()) }.getOrNull()
    }
  }
}