package org.igorsmotto.kariba.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import org.igorsmotto.kariba.cli.CLIInterface
import org.igorsmotto.kariba.input.PlayerName
import org.igorsmotto.kariba.repository.GameRepository
import org.igorsmotto.kariba.usecases.GameLoop
import org.igorsmotto.kariba.usecases.InitializeGame

class PlayGame(
    private val gameRepository: GameRepository,
) : CliktCommand(name = "start", help = "Start a new game") {

    private val playerNames: List<String> by argument(help = "Name of Players").multiple()

    override fun run() {
        val players = playerNames.map {
            PlayerName(it)
        }
        val gameState = InitializeGame(gameRepository).execute(players)

        gameState.fold(
            onSuccess = {
//                echo(it)
            },
            onFailure = {
                echo(it.message)
            }
        )

        val winner = GameLoop(CLIInterface, gameRepository).execute()
        echo("Congrats ${winner.name} on winning!!!")
    }
}