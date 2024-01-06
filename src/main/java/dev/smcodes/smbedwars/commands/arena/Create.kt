package dev.smcodes.smbedwars.commands.arena

import dev.smcodes.smbedwars.SMBedwars
import dev.smcodes.smbedwars.commands.SubCommand
import dev.smcodes.smbedwars.database.Connection
import dev.smcodes.smbedwars.database.models.Arenas
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.ConversationFactory
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class Create : SubCommand() {
    private var conversationFactory: ConversationFactory = ConversationFactory(SMBedwars.instace)
        .withModality(true)
        .withFirstPrompt(ArenaNamePrompt())

    override fun onCommand(player: Player?, args: Array<out String>?) {
        val conversation = conversationFactory.buildConversation(player)
        conversation.isLocalEchoEnabled = false
        conversation.begin()
    }

    private fun create(input: String) {
        transaction (Connection.db) {
            Arenas.insert {
                it[name] = input
            }
        }
    }

    private inner class ArenaNamePrompt : StringPrompt() {
        override fun getPromptText(context: ConversationContext?): String {
            return "§7Digite o nome da arena:"
        }

        override fun acceptInput(context: ConversationContext?, input: String?): Prompt? {
            if (input != null) {
                this@Create.create(input)
                context?.forWhom?.sendRawMessage("§aArena criada com sucesso!")
            }

            return null
        }

    }
}