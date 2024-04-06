/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands

import dev.nikomaru.advancerailway.Line3D
import dev.nikomaru.advancerailway.Point3D
import dev.nikomaru.advancerailway.utils.display.DisplayLines
import org.bukkit.command.CommandSender
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("advancerailway", "ar")
class DisplayLineCommand {
    @Subcommand("line")
    suspend fun displayLine(sender: CommandSender, first: Point3D, second: Point3D) {
        if (sender !is org.bukkit.entity.Player) {
            sender.sendMessage("You must be a player to use this command")
            return
        }
        val displayLines = DisplayLines()
        displayLines.addTarget(sender)
        displayLines.addDisplayObject(Line3D(first, second))
        displayLines.display()
        sender.sendMessage("Displayed line")
    }
}