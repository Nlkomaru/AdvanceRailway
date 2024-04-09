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
import dev.nikomaru.advancerailway.file.value.GroupId
import dev.nikomaru.advancerailway.file.value.RailwayId
import dev.nikomaru.advancerailway.file.value.StationId
import kotlinx.serialization.Serializable

@Serializable
data class RailwayData(
    val id: RailwayId,
    val group: GroupId?,

    val lineType: LineType,
    val line: Line3D,
    val fromStation: StationId,
    val toStation: StationId,
    val timeRequired: Long, //秒
    val startPoint: Point3D,
    val endPoint: Point3D,
    val directionPoint: Point3D
) {
    fun toCommandString(): String {
        return "ar register ${id.name} $startPoint $directionPoint $endPoint"
    }
}
