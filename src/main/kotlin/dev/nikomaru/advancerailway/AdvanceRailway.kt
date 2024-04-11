/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway

import com.comphenix.protocol.ProtocolLibrary
import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import dev.nikomaru.advancerailway.commands.railway.RailwayMainCommand
import dev.nikomaru.advancerailway.file.FileLoader
import dev.nikomaru.advancerailway.listener.RailClickEvent
import dev.nikomaru.advancerailway.utils.command.Point3DParser.registerPoint3DParser
import org.bukkit.Bukkit
import org.bukkit.command.defaults.HelpCommand
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import revxrsal.commands.bukkit.BukkitCommandHandler
import revxrsal.commands.ktx.supportSuspendFunctions
import xyz.jpenilla.squaremap.api.*


open class AdvanceRailway: SuspendingJavaPlugin() {
    companion object {
        lateinit var plugin: AdvanceRailway
            private set
    }

    override suspend fun onEnableAsync() { // Plugin startup logic
        plugin = this
        setCommand()
        setEventHandlers()
        setupKoin()
        settingMap()
        FileLoader.load()
    }

    private fun settingMap() {
        val squaremapApi = SquaremapProvider.get()
        val world = Bukkit.getWorld("world")
        val mapWorld: MapWorld = squaremapApi.getWorldIfEnabled(BukkitAdapter.worldIdentifier(world)).orElse(null)
        val provider =
            SimpleLayerProvider.builder("advanced Railway").layerPriority(1).defaultHidden(true).zIndex(0).build()
        mapWorld.layerRegistry().register(Key.of("advanced_Railway"), provider)

        loadKoinModules(module {
            single { squaremapApi }
            single { provider }
        })
    }

    private fun setupKoin() {
        val appModule = module {
            single<AdvanceRailway> { this@AdvanceRailway }
            single { ProtocolLibrary.getProtocolManager() }
        }

        GlobalContext.getOrNull() ?: GlobalContext.startKoin {
            modules(appModule)
        }
    }

    override suspend fun onDisableAsync() { // Plugin shutdown logic
    }

    private fun setCommand() {
        val handler = BukkitCommandHandler.create(this)

        handler.setSwitchPrefix("--")
        handler.setFlagPrefix("--")
        handler.supportSuspendFunctions()

        handler.setHelpWriter { command, _ ->
            java.lang.String.format(
                """
                <color:yellow>command: <color:gray>%s %s
                <color:yellow>description: <color:gray>%s
                
                """.trimIndent(),
                command.path.toRealString(),
                command.usage,
                command.description,
            )
        }
        handler.registerPoint3DParser()

        with(handler) {
            register(HelpCommand(), RailwayMainCommand())
        }
    }

    private fun setEventHandlers() { // Register event handlers
        server.pluginManager.registerSuspendingEvents(RailClickEvent(), this)
    }
}