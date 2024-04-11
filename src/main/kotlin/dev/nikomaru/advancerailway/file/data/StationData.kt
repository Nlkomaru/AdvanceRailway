/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.file.data

import dev.nikomaru.advancerailway.Point3D
import dev.nikomaru.advancerailway.file.utils.ColorSerializer
import dev.nikomaru.advancerailway.file.utils.WorldSerializer
import dev.nikomaru.advancerailway.file.value.StationId
import kotlinx.serialization.Serializable
import org.bukkit.World
import java.awt.Color

@Serializable
data class StationData(
    val id: StationId,
    val name: String,
    val numbering: String?,
    val world: @Serializable(with = WorldSerializer::class) World,
    val point: Point3D,
    val overrideSize: Double?,
    val color: @Serializable(with = ColorSerializer::class) Color?,
)
