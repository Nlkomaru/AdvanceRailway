/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands.station

import dev.nikomaru.advancerailway.Point3D
import dev.nikomaru.advancerailway.file.data.StationData
import dev.nikomaru.advancerailway.file.value.StationId
import dev.nikomaru.advancerailway.utils.Utils.toPoint3D
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("ar station", "advancerailway station")
class StationMainCommand {
    @Subcommand("add")
    fun add(sender: CommandSender, id: String, name: String, inputPoint: Point3D?) { // Add station
        if (sender !is Player && inputPoint == null) {
            println("You must enter the point")
            return
        }
        val point = inputPoint ?: (sender as Player).location.toPoint3D()
        val world = if (sender is Player) sender.world else Bukkit.getWorld("world")!!
        val stationId = StationId(id)
        val data = StationData(stationId, name, null, world, point, null, null)
        data.save()
        sender.sendMessage("Station added")
    }

}