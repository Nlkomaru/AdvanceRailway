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
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("ar", "advancerailway")
class InfoCommand: KoinComponent {
    val plugin: AdvanceRailway by inject()

    @Subcommand("info")
    fun info(sender: CommandSender) {
        sender.sendMessage("AdvanceRailway Information")
        sender.sendMessage("Version: ${plugin.pluginMeta.version}")
        sender.sendMessage("Author: ${plugin.pluginMeta.authors.joinToString(",")}")
        sender.sendMessage("Website: ${plugin.pluginMeta.website}")

    }
}