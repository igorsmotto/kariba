package org.igorsmotto.kariba.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import org.igorsmotto.kariba.usecases.StartGame

class PlayGame(
  private val startGame: StartGame
) : CliktCommand(name = "start", help = "Start a new game") {

  private val playerNames: List<String> by argument(help = "Name of Players").multiple()
  private val deckSize: Int by option("-s", "--size", help = "Size of the deck").int().default(64)

  override fun run() {
    startGame.execute(playerNames, deckSize).fold(
      onSuccess = { echo("Congratulations ${it.winner.name} for winning Kariba!!") },
      onFailure = { echo("Something went wrong: ${it.message}") }
    )
  }
}