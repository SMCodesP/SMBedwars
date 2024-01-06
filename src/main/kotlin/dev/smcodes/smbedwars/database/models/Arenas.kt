package dev.smcodes.smbedwars.database.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Arenas: IntIdTable() {
    val name = varchar("name", 50).uniqueIndex()
    val lobby = reference("lobby", Places).nullable()
    val spawn = reference("spawn", Places).nullable()
}
