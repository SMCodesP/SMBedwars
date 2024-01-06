package dev.smcodes.smbedwars.commands

import org.bukkit.entity.Player

abstract class SubCommand {
    open fun onCommand(player: Player?, args: Array<out String>?) {}
}