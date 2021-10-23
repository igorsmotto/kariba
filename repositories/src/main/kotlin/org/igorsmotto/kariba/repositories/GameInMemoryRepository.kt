package org.igorsmotto.kariba.repositories

import org.igorsmotto.kariba.entities.Lake
import org.igorsmotto.kariba.entities.Player
import org.igorsmotto.kariba.entities.ShuffledDeck
import org.igorsmotto.kariba.repository.GameRepository

class GameInMemoryRepository : GameRepository {

    private var gameStarted = false
    private lateinit var players: List<Player>
    private lateinit var lake: Lake
    private lateinit var deck: ShuffledDeck

    override fun saveGame(
        players: List<Player>,
        lake: Lake,
        deck: ShuffledDeck
    ) {
        synchronized(this) {
            this.players = players
            this.lake = lake
            this.deck = deck
            this.gameStarted = true
        }
    }

    override fun loadGame(): Triple<List<Player>, Lake, ShuffledDeck> {
        synchronized(this) {
            if (gameStarted) {
                return Triple(players, lake, deck)
            }
            throw IllegalStateException("We're trying to load a game, but it hasn't started")
        }
    }
}