/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.file.data

import dev.nikomaru.advancerailway.Line3D
import dev.nikomaru.advancerailway.Point3D
import kotlinx.serialization.Serializable
import java.awt.Color

@Serializable
data class RailwayData(
    val name: String,
    val group: String,
    val color: @Serializable(with = ColorSerializer::class) Color,
    val lineType: LineType,
    val line: @Serializable(Line3DSerializer::class) Line3D,
    val fromStation: @Serializable(StationNameSerializer::class) StationName,
    val toStation: @Serializable(StationNameSerializer::class) StationName,
    val startPoint: @Serializable(Point3DSerializer::class) Point3D,
    val endPoint: @Serializable(Point3DSerializer::class) Point3D,
    val directionPoint: @Serializable(Point3DSerializer::class) Point3D
) {
    fun toCommandString(): String {
        return "advancerailway register $name ${fromStation.name} ${toStation.name} $startPoint $directionPoint $endPoint"
    }
}
