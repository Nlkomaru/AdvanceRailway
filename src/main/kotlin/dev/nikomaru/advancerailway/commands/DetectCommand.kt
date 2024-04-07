/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands

import dev.nikomaru.advancerailway.listener.RailClickEvent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("advancerailway", "ar")
class DetectCommand {
    @Subcommand("detect")
    fun detect(sender: CommandSender) {
        if (sender !is Player) {
            sender.sendMessage("This command can only be executed by players.")
            return
        }
        sender.sendRichMessage("Click on a block to detect the railway.")
        RailClickEvent.detect.add(sender)
    }
}