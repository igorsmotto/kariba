package org.igorsmotto.kariba

import org.igorsmotto.kariba.InstanceCreation.gameRepository
import org.igorsmotto.kariba.cli.cli

fun main(args: Array<String>) {
    cli(args, gameRepository)
}

