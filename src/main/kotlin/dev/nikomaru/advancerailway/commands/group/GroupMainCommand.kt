/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands.group

import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.file.data.GroupData
import dev.nikomaru.advancerailway.file.value.GroupId
import dev.nikomaru.advancerailway.file.value.StationId
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand
import java.awt.Color

@Command("ar group", "advancerailway group")
class GroupMainCommand: KoinComponent {
    val plugin: AdvanceRailway by inject()

    @Subcommand("add")
    fun add(sender: CommandSender, id: String, name: String) {
        id.matches(Regex("[a-zA-Z0-9_]+")) || run {
            sender.sendRichMessage("Error: Invalid railway ID \"[a-zA-Z0-9_]+\"")
            return
        }
        val groupId = GroupId(id)
        val data = GroupData(groupId, name, Color.getHSBColor(Math.random().toFloat(), 1.0f, 1.0f))
        data.save()
        sender.sendRichMessage("Group added")
    }

    @Subcommand("remove")
    fun remove(sender: CommandSender, id: StationId) {
        val file = plugin.dataFolder.resolve("data").resolve("groups").resolve("${id.value}.json")
        if (!file.exists()) {
            sender.sendRichMessage("Group not found")
            return
        }
        file.delete()
        sender.sendRichMessage("Group removed")
    }

}