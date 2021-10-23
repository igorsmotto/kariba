package org.igorsmotto.kariba.repository

import org.igorsmotto.kariba.entities.Lake
import org.igorsmotto.kariba.entities.Player
import org.igorsmotto.kariba.entities.ShuffledDeck

interface GameRepository {
    fun saveGame(players: List<Player>, lake: Lake, deck: ShuffledDeck)
    fun loadGame(): Triple<List<Player>, Lake, ShuffledDeck>
}