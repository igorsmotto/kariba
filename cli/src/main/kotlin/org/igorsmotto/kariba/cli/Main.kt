package org.igorsmotto.kariba.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import org.igorsmotto.kariba.cli.commands.LeaderBoard
import org.igorsmotto.kariba.cli.commands.PlayGame
import org.igorsmotto.kariba.repository.GameRepository

class Kariba : CliktCommand() {
    override fun run() = Unit
}

fun cli(
    args: Array<String>,
    gameRepository: GameRepository
) = Kariba()
    .subcommands(PlayGame(gameRepository), LeaderBoard())
    .main(args)