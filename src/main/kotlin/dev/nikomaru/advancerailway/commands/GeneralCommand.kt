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
import dev.nikomaru.advancerailway.file.FileLoader
import dev.nikomaru.advancerailway.file.type.ExportFileType
import dev.nikomaru.advancerailway.listener.RailClickEvent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Default
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.help.CommandHelp

@Command("ar", "advancerailway")
class GeneralCommand: KoinComponent {
    val plugin: AdvanceRailway by inject()

    @Subcommand("info")
    fun info(sender: CommandSender) {
        sender.sendMessage("AdvanceRailway Information")
        sender.sendMessage("Version: ${plugin.pluginMeta.version}")
        sender.sendMessage("Author: ${plugin.pluginMeta.authors.joinToString(",")}")
        sender.sendMessage("Website: ${plugin.pluginMeta.website}")

    }

    @Subcommand("help")
    @Description("Shows the help menu")
    fun help(sender: CommandSender, helpEntries: CommandHelp<String>, @Default("1") page: Int) {
        for (entry in helpEntries.paginate(page, 7))  // 7 entries per page
            sender.sendRichMessage(entry)
    }

    @Subcommand("inspect")
    fun detect(sender: CommandSender) {
        if (sender !is Player) {
            sender.sendMessage("This command can only be executed by players.")
            return
        }
        sender.sendRichMessage("Click on a block to detect the railway.")
        RailClickEvent.detect.add(sender)
    }

    @Subcommand("reload")
    fun reload(sender: CommandSender) {
        FileLoader.load()
        sender.sendMessage("Reloaded map and config data.")
    }

    @Subcommand("export")
    fun export(sender: CommandSender, type: ExportFileType) { //TODO: Implement export
        sender.sendMessage("This feature is not implemented yet.")
    }
}