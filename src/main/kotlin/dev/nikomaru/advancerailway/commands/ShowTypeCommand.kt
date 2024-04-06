/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.commands

import dev.nikomaru.advancerailway.utils.RailUtils
import dev.nikomaru.advancerailway.utils.display.LuminescenceShulker
import kotlinx.coroutines.delay
import org.bukkit.block.data.Rail
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand

@Command("advancerailway", "ar")
class ShowTypeCommand {
    @Subcommand("show")
    suspend fun showType(sender: Player) {
        val block = sender.getTargetBlock(null, 5)
        val blockState = block.blockData
        if (blockState !is Rail) {
            sender.sendMessage("You must see a rail")
            return
        }
        val locations = RailUtils.getRailAvailableDirection(blockState.shape).map { (x, y, z) ->
            block.location.clone().add(x.toDouble(), y.toDouble(), z.toDouble())
        }
        val luminescenceShulker = LuminescenceShulker()
        luminescenceShulker.addTarget(sender)
        locations.forEach {
            luminescenceShulker.addDisplayObject(it)
        }
        luminescenceShulker.display()
        delay(5000)
        luminescenceShulker.stop()
        sender.sendMessage("Displayed rail type")

    }
}