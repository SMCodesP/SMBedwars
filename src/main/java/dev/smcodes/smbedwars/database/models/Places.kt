package dev.smcodes.smbedwars.database.models

import dev.smcodes.smbedwars.database.Connection
import org.bukkit.Bukkit
import org.bukkit.Location
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction

object Places: IntIdTable() {
    val world = uuid("world")
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val yaw = float("yaw")
    val pitch = float("pitch")

    fun createLocation(location: Location): InsertStatement<Number> {
        return transaction (Connection.db) {
            val place = Places.insert {
                it[world] = location.world.uid
                it[y] = location.y
                it[x] = location.x
                it[z] = location.z
                it[yaw] = location.yaw
                it[pitch] = location.pitch
            }

            return@transaction place
        }
    }
}

fun ResultRow.toLocation(): Location {
    val worldValue = this[Places.world]
    val xValue = this[Places.x]
    val yValue = this[Places.y]
    val zValue = this[Places.z]
    val yawValue = this[Places.yaw]
    val pitchValue = this[Places.pitch]

    val world = Bukkit.getWorld(worldValue)
    val location = Location(world, xValue, yValue, zValue, yawValue, pitchValue)

    return location
}