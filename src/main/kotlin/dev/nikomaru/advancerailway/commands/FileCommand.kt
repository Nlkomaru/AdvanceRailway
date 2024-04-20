/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands

import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.file.data.*
import dev.nikomaru.advancerailway.utils.Utils.csv
import dev.nikomaru.advancerailway.utils.Utils.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.bukkit.annotation.CommandPermission

@OptIn(ExperimentalSerializationApi::class)
@Command("ar file", "advancerailway file")
@CommandPermission("advancerailway.command.file")
class FileCommand: KoinComponent {
    val plugin: AdvanceRailway by inject()

    @Subcommand("export")
    fun export(sender: CommandSender, dataType: DataType, fileType: FileType) {
        val stringFormat = when (fileType) {
            FileType.CSV -> csv
            FileType.JSON -> json
        }
        val extension = when (fileType) {
            FileType.CSV -> "csv"
            FileType.JSON -> "json"
        }
        val data = when (dataType) {
            DataType.GROUP -> {
                val file = plugin.dataFolder.resolve("data").resolve("groups").listFiles()!!
                val data = file.map { json.decodeFromString<GroupData>(it.readText()) }
                stringFormat.encodeToString(data)
            }

            DataType.RAILWAY -> {
                val file = plugin.dataFolder.resolve("data").resolve("railways").listFiles()!!
                val data = file.map { json.decodeFromString<RailwayData>(it.readText()) }
                stringFormat.encodeToString(data)
            }

            DataType.STATION -> {
                val file = plugin.dataFolder.resolve("data").resolve("stations").listFiles()!!
                val data = file.map { json.decodeFromString<StationData>(it.readText()) }
                stringFormat.encodeToString(data)
            }
        }
        val uuid = java.util.UUID.randomUUID()
        plugin.dataFolder.resolve("export-${uuid}.$extension").writeText(data)
        sender.sendMessage("Exported to export-${uuid}.$extension")
    }

    @Subcommand("import")
    fun import(sender: CommandSender, dataType: DataType, fileName: String) {
        val data = plugin.dataFolder.resolve("import").resolve(fileName).readText()
        val extension = fileName.split(".").last()
        val stringFormat = when (extension) {
            "csv" -> csv
            "json" -> json
            else -> return
        }
        when (dataType) {
            DataType.GROUP -> {
                val fileDir = plugin.dataFolder.resolve("data").resolve("groups")
                fileDir.listFiles()?.forEach { it.delete() }
                fileDir.mkdirs()
                val groupData = stringFormat.decodeFromString<List<GroupData>>(data)
                groupData.forEach {
                    val file = plugin.dataFolder.resolve("data").resolve("groups").resolve("${it.groupId.value}.json")
                    file.writeText(json.encodeToString(it))
                }
            }

            DataType.RAILWAY -> {
                val fileDir = plugin.dataFolder.resolve("data").resolve("railways")
                fileDir.listFiles()?.forEach { it.delete() }
                fileDir.mkdirs()
                val railwayData = stringFormat.decodeFromString<List<RailwayData>>(data)
                railwayData.forEach {
                    val file = plugin.dataFolder.resolve("data").resolve("railways").resolve("${it.id.value}.json")
                    file.writeText(json.encodeToString(it))
                }
            }

            DataType.STATION -> {
                val fileDir = plugin.dataFolder.resolve("data").resolve("stations")
                fileDir.listFiles()?.forEach { it.delete() }
                fileDir.mkdirs()
                val stationData = stringFormat.decodeFromString<List<StationData>>(data)
                stationData.forEach {
                    val file = fileDir.resolve("${it.stationId.value}.json")
                    file.writeText(json.encodeToString(it))
                }
            }
        }
        sender.sendMessage("Imported $fileName")
    }
}