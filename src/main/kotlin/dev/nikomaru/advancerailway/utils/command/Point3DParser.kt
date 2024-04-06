/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.utils.command

import dev.nikomaru.advancerailway.Point3D
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import revxrsal.commands.bukkit.BukkitCommandHandler
import revxrsal.commands.bukkit.sender
import revxrsal.commands.command.CommandActor
import revxrsal.commands.command.ExecutableCommand
import revxrsal.commands.process.ValueResolver

object Point3DParser: ValueParser<Point3D>() {
    override fun suggestions(args: List<String>, sender: CommandSender, command: ExecutableCommand): Set<String> {
        if (sender !is Player) run {
            println("You must be a player to use this command")
            return emptySet()
        }
        val loc = sender.location
        val s = "${loc.x.toInt()} ${loc.y.toInt()} ${loc.z.toInt()}"
        return setOf(s)
    }

    override fun resolve(context: ValueResolver.ValueResolverContext): Point3D {
        val x = context.pop().toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number")
        val y = context.pop().toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number")
        val z = context.pop().toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number")
        return Point3D(x, y, z)
    }

    fun BukkitCommandHandler.registerPoint3DParser() {
        val handler = this
        handler.autoCompleter.registerParameterSuggestions(
            Point3D::class.java,
        ) { args: List<String>, sender: CommandActor, command: ExecutableCommand ->
            suggestions(args, sender.sender, command)
        }
        handler.registerValueResolver(Point3D::class.java, this@Point3DParser)
    }

}