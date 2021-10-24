package org.igorsmotto.kariba

import org.igorsmotto.kariba.cli.cli
import org.igorsmotto.kariba.cli.interfaces.CLIInterface
import org.igorsmotto.kariba.usecases.MainGame
import org.igorsmotto.kariba.usecases.StartGame

fun main(args: Array<String>) {
  val startGame = StartGame(MainGame(CLIInterface))

  cli(args, startGame)
}

