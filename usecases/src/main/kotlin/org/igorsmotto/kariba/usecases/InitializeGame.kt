package org.igorsmotto.kariba.usecases

import org.igorsmotto.kariba.entities.Deck
import org.igorsmotto.kariba.entities.Lake
import org.igorsmotto.kariba.entities.Player
import org.igorsmotto.kariba.entities.dealHand
import org.igorsmotto.kariba.input.PlayerName
import org.igorsmotto.kariba.output.GameState
import org.igorsmotto.kariba.output.toLakeState
import org.igorsmotto.kariba.output.toPlayerState
import org.igorsmotto.kariba.repository.GameRepository

class InitializeGame(
    private val gameRepository: GameRepository
) {
    fun execute(playerNames: List<PlayerName>): Result<GameState> {
        if (playerNames.size < 2) {
            return Result.failure(InsufficientNumberOfPlayers)
        }
        val shuffledDeck = Deck.shuffle()

        val (players, remainingDeck) = playerNames.fold(emptyList<Player>() to shuffledDeck) { (players, deck), playerName ->
            val (handDealt, remainingDeck) = dealHand(deck)
            val player = Player(playerName.name, hand = handDealt, points = 0, order = players.size)
            players.plus(player) to remainingDeck
        }

        gameRepository.saveGame(players, Lake(), remainingDeck)

        return GameState(remainingDeck.size, players.map { it.toPlayerState() }, Lake().toLakeState())
            .let {
                Result.success(it)
            }
    }
}

object InsufficientNumberOfPlayers : Throwable("There should be at least 2 players in order to play Kariba")
