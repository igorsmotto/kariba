package org.igorsmotto.kariba.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import org.igorsmotto.kariba.cli.CLIInterface
import org.igorsmotto.kariba.input.PlayerName
import org.igorsmotto.kariba.usecases.StartGame

class PlayGame : CliktCommand(name = "start", help = "Start a new game") {

  private val playerNames: List<String> by argument(help = "Name of Players").multiple()

  override fun run() {
    val players = playerNames.map {
      PlayerName(it)
    }
    val winner = StartGame(CLIInterface).execute(players)
  }
}