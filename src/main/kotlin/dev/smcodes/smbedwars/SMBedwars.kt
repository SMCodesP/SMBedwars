package dev.smcodes.smbedwars

import dev.smcodes.smbedwars.commands.arena.Arena
import dev.smcodes.smbedwars.database.Connection
import org.bukkit.Bukkit
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.plugin.java.JavaPlugin


class SMBedwars : JavaPlugin() {

    private var console: ConsoleCommandSender = Bukkit.getConsoleSender()
    companion object {
        var instace: SMBedwars? = null
    }

    override fun onEnable() {
        instace = this

        Connection()
        this.getCommand("arena").executor = Arena()

        console.sendMessage("§4-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
        console.sendMessage("§7Status do plugin: §2Iniciado")
        console.sendMessage("§7Autor do plugin: §6SMCodes")
        console.sendMessage("§7Versão: §21.0.0")
        console.sendMessage("§4-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")

    }

    override fun onDisable() {
        console.sendMessage("§4-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
        console.sendMessage("§7Status do plugin: §2Desligou")
        console.sendMessage("§7Autor do plugin: §6SMCodes")
        console.sendMessage("§7Versão: §21.0.0")
        console.sendMessage("§4-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    }

}
