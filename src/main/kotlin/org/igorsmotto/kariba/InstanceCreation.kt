package org.igorsmotto.kariba

import org.igorsmotto.kariba.repositories.GameInMemoryRepository

object InstanceCreation {
    val gameRepository = GameInMemoryRepository()
}