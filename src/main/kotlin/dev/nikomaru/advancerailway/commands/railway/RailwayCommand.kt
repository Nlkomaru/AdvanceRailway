/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands.railway

import dev.nikomaru.advancerailway.Point3D
import org.bukkit.command.CommandSender
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("advancerailway railway", "ar railway")
class RailwayCommand {
    @Subcommand("register")
    fun register(
        sender: CommandSender, id: String, name: String, startPoint: Point3D, directionPoint: Point3D, endPoint: Point3D
    ) {
        sender.sendMessage("Registering railway...")

    }

}