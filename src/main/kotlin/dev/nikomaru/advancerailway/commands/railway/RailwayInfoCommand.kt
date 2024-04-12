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
import dev.nikomaru.advancerailway.file.value.RailwayId
import dev.nikomaru.advancerailway.utils.RailwayUtils
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand
import kotlin.math.ceil

@Command("ar railway", "advancerailway railway")
class RailwayInfoCommand: KoinComponent {
    val plugin: AdvanceRailway by inject()

    @Subcommand("info")
    fun info(sender: CommandSender, railwayId: RailwayId) {
        val data = RailwayUtils.getRailwayData(railwayId) ?: run {
            sender.sendMessage("Railway not found")
            return
        }
        sender.sendMessage("Railway ID: ${data.id.value}")
        sender.sendMessage("Railway Stations: ${data.toStation} -> ${data.fromStation}")
        sender.sendMessage("Railway Length: ${ceil(data.timeRequired / 6.0) / 10} minutes")
    }

    @Subcommand("list")
    fun list(sender: CommandSender) {
        val list = plugin.dataFolder.resolve("data").resolve("railways").listFiles()?.map { it.nameWithoutExtension }
            ?: emptyList()
        list.forEach {
            sender.sendMessage(it)
        }
    }
}