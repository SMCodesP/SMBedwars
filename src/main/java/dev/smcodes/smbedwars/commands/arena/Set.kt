package dev.smcodes.smbedwars.commands.arena

import dev.smcodes.smbedwars.commands.SubCommand
import dev.smcodes.smbedwars.database.Connection
import dev.smcodes.smbedwars.database.models.Arenas
import dev.smcodes.smbedwars.database.models.Places
import dev.smcodes.smbedwars.utils.PlayerException
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class Set : SubCommand() {
    override fun onCommand(player: Player?, args: Array<out String>?) {
        if (player !== null) {
            try {
                val fieldUpdate = args?.getOrNull(0) ?: throw PlayerException("§cPor favor informe no comando o que deseja alterar!")
                val arenaName = args.getOrNull(1) ?: throw PlayerException("§cNão foi informado uma arena válida!")
                val location = player.location

                transaction (Connection.db) {
                    val arena = Arenas.select {
                        Arenas.name eq arenaName
                    }.firstOrNull() ?: throw PlayerException("§cNão foi informado uma arena válida!")

                    val place = Places.createLocation(location)

                    val field = when (fieldUpdate) {
                        "spawn" -> Arenas.spawn
                        "lobby" -> Arenas.lobby
                        else -> throw PlayerException("§cNão é possível setar §e$fieldUpdate para essa arena!")
                    }

                    Arenas.update({
                        Arenas.id eq arena[Arenas.id]
                    }) {
                        it[field] = place[Places.id]
                    }

                    player.sendMessage("§aO $fieldUpdate foi trocado com sucesso!")
                }
            } catch (e: PlayerException) {
                player.sendMessage(e.message)
            }
        }
    }
}