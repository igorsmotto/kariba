package org.igorsmotto.kariba.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import org.igorsmotto.kariba.cli.commands.PlayGame
import org.igorsmotto.kariba.usecases.StartGame

class Kariba : CliktCommand() {
  override fun run() = Unit
}

fun cli(
  args: Array<String>,
  startGame: StartGame
) =
  Kariba()
    .subcommands(PlayGame(startGame))
    .main(args)