/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.listener

import arrow.core.Either
import dev.nikomaru.advancerailway.Point3D
import dev.nikomaru.advancerailway.utils.RailwayUtils
import dev.nikomaru.advancerailway.utils.RailwayUtils.railFinishDetect
import dev.nikomaru.advancerailway.utils.coroutines.async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.bukkit.block.data.Rail
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class RailClickEvent: Listener {
    companion object {
        val detect = arrayListOf<Player>()
    }

    @EventHandler
    suspend fun onRailClick(event: PlayerInteractEvent) {
        val player = event.player
        if (player !in detect) {
            return
        }
        detect.remove(player)
        withContext(Dispatchers.async) {
            val block = event.clickedBlock ?: return@withContext
            val blockState = block.blockData
            if (blockState !is Rail) {
                return@withContext
            }

            player.sendMessage("Detecting railway...")
            val locate = block.location
            val startPoint = locate.let { Point3D(it.x, it.y, it.z) }
            val availableLocations =
                RailwayUtils.getRailAvailableDirection(block.location.let { Point3D(it.x, it.y, it.z) })
                    .map { (x, y, z) ->
                    locate.clone().add(x.toDouble(), y.toDouble(), z.toDouble())
                }
            val detectedRailLocations = availableLocations.filter { it.block.blockData is Rail }
            if (detectedRailLocations.count() == 1) {
                player.sendMessage("This is a first or last rail.")
            } else {
                player.sendMessage("This is a middle rail.")
            }
            val res = detectedRailLocations.map { detectedPlace ->
                async {
                    railFinishDetect(locate.let { Point3D(it.x, it.y, it.z) },
                                     detectedPlace.let { Point3D(it.x, it.y, it.z) })
                }
            }.awaitAll()
            res.forEach {
                when (it) {
                    is Either.Right -> {
                        player.sendMessage("Railway end detected.")
                        player.sendMessage("Railway Data : $startPoint -> ${it.value}")
                    }

                    is Either.Left -> {
                        player.sendMessage("Error: ${it.value}")
                    }
                }
            }
        }
    }
}