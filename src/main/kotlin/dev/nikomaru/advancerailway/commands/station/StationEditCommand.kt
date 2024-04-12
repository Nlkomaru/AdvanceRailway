/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands.station

import arrow.core.Either
import dev.nikomaru.advancerailway.Point3D
import dev.nikomaru.advancerailway.file.value.StationId
import dev.nikomaru.advancerailway.utils.StationUtils
import dev.nikomaru.advancerailway.utils.Utils.toPoint3D
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("ar station", "advancerailway station")
class StationEditCommand {
    @Subcommand("rename")
    suspend fun rename(sender: CommandSender, stationId: StationId, newName: String) {
        val data = when (val res = StationUtils.getStationData(stationId)) {
            is Either.Left -> {
                sender.sendMessage("Station not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        data.copy(name = newName).save()
        sender.sendMessage("Station name set")
    }

    @Subcommand("set location")
    suspend fun setLocation(sender: CommandSender, stationId: StationId, inputPoint: Point3D?) {
        if (sender !is Player && inputPoint == null) {
            println("You must enter the point")
            return
        }
        val point = inputPoint ?: (sender as Player).location.toPoint3D()
        val world = if (sender is Player) sender.world else Bukkit.getWorld("world")!!
        val data = when (val res = StationUtils.getStationData(stationId)) {
            is Either.Left -> {
                sender.sendMessage("Station not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        data.copy(point = point, world = world).save()
        sender.sendMessage("Station location set")
    }

    @Subcommand("set numbering")
    suspend fun setNumbering(sender: CommandSender, stationId: StationId, numbering: String) {
        val data = when (val res = StationUtils.getStationData(stationId)) {
            is Either.Left -> {
                sender.sendMessage("Station not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        data.copy(numbering = numbering).save()
        sender.sendMessage("Station numbering set")
    }
}