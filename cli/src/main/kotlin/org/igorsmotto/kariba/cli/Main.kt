package org.igorsmotto.kariba.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import org.igorsmotto.kariba.cli.commands.PlayGame

class Kariba : CliktCommand() {
  override fun run() = Unit
}

fun cli(args: Array<String>) =
  Kariba()
    .subcommands(PlayGame())
    .main(args)