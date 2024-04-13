/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands.railway

import arrow.core.Either
import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.file.type.LineType
import dev.nikomaru.advancerailway.file.value.GroupId
import dev.nikomaru.advancerailway.file.value.RailwayId
import dev.nikomaru.advancerailway.file.value.StationId
import dev.nikomaru.advancerailway.utils.RailwayUtils
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("ar railway", "advancerailway railway")
class RailwayEditCommand: KoinComponent {
    val plugin: AdvanceRailway by inject()

    @Subcommand("set line-type")
    suspend fun setLineType(sender: CommandSender, railwayId: RailwayId, lineType: LineType) {
        val data = when (val res = RailwayUtils.getRailwayData(railwayId)) {
            is Either.Left -> {
                sender.sendRichMessage("Railway not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        data.copy(lineType = lineType).save()
        sender.sendRichMessage("Line type set to $lineType")
    }

    @Subcommand("set group")
    suspend fun setGroup(sender: CommandSender, railwayId: RailwayId, groupId: GroupId) {
        val data = when (val res = RailwayUtils.getRailwayData(railwayId)) {
            is Either.Left -> {
                sender.sendRichMessage("Railway not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        data.copy(group = groupId).save()
        sender.sendRichMessage("Group set to $groupId")
    }

    @Subcommand("unset group")
    suspend fun unsetGroup(sender: CommandSender, railwayId: RailwayId) {
        val data = when (val res = RailwayUtils.getRailwayData(railwayId)) {
            is Either.Left -> {
                sender.sendRichMessage("Railway not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        data.copy(group = null).save()
        sender.sendRichMessage("Group unset")
    }

    @Subcommand("set from-station")
    suspend fun setFromStation(sender: CommandSender, railwayId: RailwayId, stationId: StationId) {
        val data = when (val res = RailwayUtils.getRailwayData(railwayId)) {
            is Either.Left -> {
                sender.sendRichMessage("Railway not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        data.copy(fromStation = stationId).save()
        sender.sendRichMessage("From station set to $stationId")

    }

    @Subcommand("set to-station")
    suspend fun setToStation(sender: CommandSender, railwayId: RailwayId, stationId: StationId) {
        val data = when (val res = RailwayUtils.getRailwayData(railwayId)) {
            is Either.Left -> {
                sender.sendRichMessage("Railway not found")
                return
            }

            is Either.Right -> {
                res.value
            }
        }
        data.copy(fromStation = stationId).save()
        sender.sendRichMessage("From station set to $stationId")

    }
}