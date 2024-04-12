/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands.railway


import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.Point3D
import dev.nikomaru.advancerailway.file.data.RailwayData
import dev.nikomaru.advancerailway.file.type.LineType
import dev.nikomaru.advancerailway.file.value.RailwayId
import dev.nikomaru.advancerailway.utils.RailwayUtils
import dev.nikomaru.advancerailway.utils.StationUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("advancerailway railway", "ar railway")
class RailwayMainCommand: KoinComponent {
    val plugin: AdvanceRailway by inject()

    @Subcommand("add")
    suspend fun register(
        sender: CommandSender, railwayId: String, startPoint: Point3D, directionPoint: Point3D, endPoint: Point3D
    ) {
        railwayId.matches(Regex("[a-zA-Z0-9_]+")) || run {
            sender.sendMessage("Error: Invalid railway ID \"[a-zA-Z0-9_]+\"")
            return
        }
        sender.sendMessage("Registering railway...")
        handleRailway(sender, railwayId, startPoint, directionPoint, endPoint, "Registered")
    }

    @Subcommand("update")
    suspend fun update(
        sender: CommandSender, railwayId: String, startPoint: Point3D, directionPoint: Point3D, endPoint: Point3D
    ) {
        sender.sendMessage("Updating railway...")
        handleRailway(sender, railwayId, startPoint, directionPoint, endPoint, "Updated")
    }

    private suspend fun handleRailway(
        sender: CommandSender,
        railwayId: String,
        startPoint: Point3D,
        directionPoint: Point3D,
        endPoint: Point3D,
        action: String
    ) {
        val line = RailwayUtils.getLine(startPoint, directionPoint, endPoint).getOrNull() ?: run {
            sender.sendMessage("Error: Failed to get line")
            return
        }
        val world = if (sender is Player) sender.world else Bukkit.getWorlds().first()
        val fromStation = StationUtils.nearStation(startPoint.toLocation(world)).getOrNull() ?: run {
            sender.sendMessage("Error: Failed to find start station")
            return
        }
        val toStation = StationUtils.nearStation(endPoint.toLocation(world)).getOrNull() ?: run {
            sender.sendMessage("Error: Failed to find end station")
            return
        }
        val railwayData = RailwayData(
            id = RailwayId(railwayId),
            group = null,
            world = world,
            lineType = LineType.UP_DOWN_LINE,
            line = line,
            fromStation = fromStation,
            toStation = toStation,
            timeRequired = line.getLength().toLong() / 10,
            startPoint = startPoint,
            endPoint = endPoint,
            directionPoint = directionPoint
        )
        railwayData.save()
        sender.sendMessage("$action railway: $railwayId")
    }

    @Subcommand("remove")
    fun remove(sender: CommandSender, railwayId: String) {
        val file = plugin.dataFolder.resolve("data").resolve("railways").resolve("$railwayId.json")
        if (!file.exists()) {
            sender.sendMessage("Error: Railway not found")
            return
        }
        file.delete()
        sender.sendMessage("Removed railway: $railwayId")
    }
}