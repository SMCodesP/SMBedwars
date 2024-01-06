package dev.smcodes.smbedwars.commands.arena

import dev.smcodes.smbedwars.commands.SubCommand
import dev.smcodes.smbedwars.database.Connection
import dev.smcodes.smbedwars.database.models.Arenas
import dev.smcodes.smbedwars.database.models.Places
import dev.smcodes.smbedwars.database.models.toLocation
import dev.smcodes.smbedwars.utils.PlayerException
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class Spawn : SubCommand() {
    override fun onCommand(player: Player?, args: Array<out String>?) {
        if (player !== null) {
            try {
                val arenaName = args?.firstOrNull() ?: throw PlayerException("§cNão foi informado uma arena válida!")

                transaction (Connection.db) {
                    val arena = Arenas.select {
                        Arenas.name eq arenaName
                    }.firstOrNull() ?: throw PlayerException("§cNão foi informado uma arena válida!")

                    val placeId = arena[Arenas.spawn] ?: throw PlayerException("§cA arena não possuí um spawn definido!")

                    val place = Places.select {
                        Places.id eq placeId
                    }.firstOrNull() ?: throw PlayerException("§cO spawn da arena não é válido!")

                    val location = place.toLocation()

                    player.teleport(location)
                }
            } catch (e: PlayerException) {
                player.sendMessage(e.message)
            }
        }
    }
}