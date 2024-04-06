/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.listener

import dev.nikomaru.advancerailway.utils.RailUtils
import org.bukkit.block.data.Rail
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class RailClickEvent: Listener {
    @EventHandler
    suspend fun onRailClick(event: org.bukkit.event.player.PlayerInteractEvent) {
        val player = event.player
        val block = event.clickedBlock ?: return
        val blockState = block.blockData
        if (blockState !is Rail) {
            return
        }
        val state = blockState.shape
        val locate = block.location
        val locations = RailUtils.getRailAvailableDirection(state).map { (x, y, z) ->
            locate.clone().add(x.toDouble(), y.toDouble(), z.toDouble())
        }
    }
}