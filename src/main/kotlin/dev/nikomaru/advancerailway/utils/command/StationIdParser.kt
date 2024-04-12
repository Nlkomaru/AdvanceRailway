/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.utils.command

import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.file.value.StationId
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import revxrsal.commands.bukkit.BukkitCommandHandler
import revxrsal.commands.bukkit.sender
import revxrsal.commands.command.CommandActor
import revxrsal.commands.command.ExecutableCommand
import revxrsal.commands.process.ValueResolver

object StationIdParser: ValueParser<StationId>(), KoinComponent {
    val plugin: AdvanceRailway by inject()
    override fun suggestions(args: List<String>, sender: CommandSender, command: ExecutableCommand): Set<String> {
        val file = plugin.dataFolder.resolve("data").resolve("stations")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.listFiles()?.map { it.nameWithoutExtension }?.toSet() ?: emptySet()
    }

    override fun resolve(context: ValueResolver.ValueResolverContext): StationId {
        val id = StationId(context.pop())
        return id
    }

    fun BukkitCommandHandler.registerStationIdParser() {
        val handler = this
        handler.autoCompleter.registerParameterSuggestions(
            StationId::class.java,
        ) { args: List<String>, sender: CommandActor, command: ExecutableCommand ->
            suggestions(args, sender.sender, command)
        }
        handler.registerValueResolver(StationId::class.java, this@StationIdParser)
    }

}