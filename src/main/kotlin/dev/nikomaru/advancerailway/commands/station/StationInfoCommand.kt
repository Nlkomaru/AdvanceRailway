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
import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.file.value.StationId
import dev.nikomaru.advancerailway.utils.StationUtils
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("ar station", "advancerailway station")
class StationInfoCommand: KoinComponent {
    val plugin: AdvanceRailway by inject()

    @Subcommand("info")
    suspend fun info(sender: CommandSender, stationId: StationId) {
        val data = when (val res = StationUtils.getStationData(stationId)) {
            is Either.Left -> {
                sender.sendMessage("Station not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        sender.sendMessage("Station ID: ${data.stationId.value}")
        sender.sendMessage("Station Name: ${data.name} <click:suggest_command:'/ar station rename ${data.stationId.value} <newName>'>[edit]</click>")
        sender.sendMessage("Station Location: ${data.world.name}:${data.point} <click:suggest_command:'/ar station set location ${data.stationId.value} <newName>'>[edit]</click>")
        sender.sendMessage("Station numbering: ${data.numbering} <click:suggest_command:'/ar station set numbering ${data.stationId.value} <newName>'>[edit]</click>")

    }

    @Subcommand("list")
    fun list(sender: CommandSender) {
        val list =
            plugin.dataFolder.resolve("data").resolve("stations").listFiles()?.map { it.nameWithoutExtension } ?: run {
                sender.sendMessage("No station found")
                return
            }
        if (list.isEmpty()) {
            sender.sendMessage("No station found")
            return
        }
        sender.sendRichMessage("Station List: <yellow>Click for details")
        list.forEach {
            sender.sendRichMessage("<click:run_command:/ar station info $it>$it</click>")
        }
    }
}