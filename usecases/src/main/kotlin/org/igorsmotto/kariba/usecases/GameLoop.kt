package org.igorsmotto.kariba.usecases

import org.igorsmotto.kariba.entities.Hand
import org.igorsmotto.kariba.entities.Lake
import org.igorsmotto.kariba.entities.Player
import org.igorsmotto.kariba.entities.ShuffledDeck
import org.igorsmotto.kariba.entities.deal
import org.igorsmotto.kariba.input.PlayerName
import org.igorsmotto.kariba.input.toCard
import org.igorsmotto.kariba.output.GameState
import org.igorsmotto.kariba.output.LakeState
import org.igorsmotto.kariba.output.PlayerState
import org.igorsmotto.kariba.output.toPlayerState
import org.igorsmotto.kariba.repository.GameRepository


class GameLoop(
    private val userInterface: UserInterface,
    private val gameRepository: GameRepository
) {

    private tailrec fun getPlayFromPlayer(player: Player): Play {
        val play = userInterface.promptPlay(
            PlayerName(player.name),
            player.hand.cards.map { card -> card.name })

        if (!validPlay(play, player)) {
            return getPlayFromPlayer(player)
        }
        return play
    }

    private tailrec fun play(players: List<Player>, currentPlayer: Player, lake: Lake, deck: ShuffledDeck): GameState {
        userInterface.printState(GameState(deck.size, players.map { it.toPlayerState() }, LakeState(lake.water)))
        if (currentPlayer.hand.cards.isEmpty()) {
            val nextPlayer = nextToPlay(players, currentPlayer.order)
            return play(players, nextPlayer, lake, deck)
        }

        val playMade = getPlayFromPlayer(currentPlayer)
        val cardsPlayed = List(playMade.quantity) { playMade.card.toCard() }.filterNotNull()
        val (updatedLake, newPoints) = lake.placeCard(cardsPlayed.first().value, cardsPlayed)

        val (cardDealt, updatedDeck) = deal(deck, playMade.quantity)

        val updatedCurrentPlayer = currentPlayer.copy(
            hand = Hand(cards = currentPlayer.hand.cards.minus(cardsPlayed).plus(cardDealt)),
            points = currentPlayer.points + newPoints
        )
        val updatedPlayers = players.minus(currentPlayer).plus(updatedCurrentPlayer)

        val nextPlayer = nextToPlay(updatedPlayers, currentPlayer.order)
        if (gameFinished(updatedPlayers, deck)) {
            return GameState(updatedDeck.size, updatedPlayers.map { it.toPlayerState() }, LakeState(updatedLake.water))
        }
        return play(updatedPlayers, nextPlayer, updatedLake, updatedDeck)
    }

    private fun nextToPlay(players: List<Player>, position: Int): Player {
        return players.first { it.order == (position + 1) % players.size }
    }

    fun execute(): PlayerState {
        val (players, lake, deck) = gameRepository.loadGame()
        val firstPlayer = players.first { it.order == 0 }

        val finalGameState = play(players, firstPlayer, lake, deck)
        userInterface.printState(finalGameState)
        return winner(finalGameState.players)
    }

    private fun winner(players: List<PlayerState>): PlayerState {
        return players.maxByOrNull { it.points }!!
    }

    private fun gameFinished(players: List<Player>, deck: ShuffledDeck): Boolean {
        if (deck.size > 0) return false
        if (players.any { it.hand.cards.isNotEmpty() }) return false
        return true
    }

    private fun validPlay(play: Play, player: Player): Boolean {
        val cardInPlay = play.card.toCard() ?: return false
        if (player.hand.cards.count { it == cardInPlay } >= play.quantity) return true
        return false
    }
}