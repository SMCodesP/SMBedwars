package dev.smcodes.smbedwars.database

import dev.smcodes.smbedwars.database.models.Arenas
import dev.smcodes.smbedwars.database.models.Places
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class Connection() {
    companion object {
        val db: Database = Database.connect(
            "jdbc:postgresql://localhost:5432/postgres",
            driver = "org.postgresql.Driver",
            user = "postgres", password = "1234")
    }

    init {
        print("Iniciando conexão...")

        transaction (db) {
            addLogger(StdOutSqlLogger)
            print("Creating Arena...")

            SchemaUtils.create(Arenas, Places)
        }
    }
}