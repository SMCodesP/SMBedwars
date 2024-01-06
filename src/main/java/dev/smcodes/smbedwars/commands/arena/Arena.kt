package dev.smcodes.smbedwars.commands.arena

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class Arena : CommandExecutor {
    private val commands = mapOf(
        "criar" to Create(),
        "set" to Set(),
        "spawn" to Spawn(),
        "lobby" to Lobby()
    )

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (sender is Player) {
            val subcommand = args?.firstOrNull()

            if (subcommand.isNullOrBlank() || !commands.containsKey(subcommand)) {
                sender.sendMessage("§7Utilize §e/$label <comando> §7ou §e/$label ajuda §7para saber mais")
                return true
            }

            val arguments = args.drop(1).toTypedArray()
            commands[subcommand]?.onCommand(sender, arguments)
            return true
        }

        return true
    }
}