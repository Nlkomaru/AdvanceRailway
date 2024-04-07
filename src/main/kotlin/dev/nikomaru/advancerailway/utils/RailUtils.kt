/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.utils

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.Line3D
import dev.nikomaru.advancerailway.Point3D
import dev.nikomaru.advancerailway.utils.coroutines.minecraft
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.data.Rail
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RailUtils: KoinComponent {
    val plugin: AdvanceRailway by inject()
    fun getRailAvailableDirection(point: Point3D): List<Triple<Int, Int, Int>> {
        val location = point.toLocation(Bukkit.getWorld("world")!!)
        val state = (location.block.blockData as Rail).shape
        return when (state) {
            Rail.Shape.EAST_WEST -> {
                listOf(
                    Triple(1, 0, 0), Triple(-1, 0, 0), Triple(1, -1, 0), Triple(-1, -1, 0)
                )
            }

            Rail.Shape.NORTH_SOUTH -> {
                listOf(
                    Triple(0, -1, -1), Triple(0, -1, 1), Triple(0, 0, -1), Triple(0, 0, 1)
                )
            }

            Rail.Shape.ASCENDING_EAST -> {
                listOf(
                    Triple(-1, 0, 0), Triple(-1, -1, 0), Triple(1, 1, 0)
                )
            }

            Rail.Shape.ASCENDING_WEST -> {
                listOf(
                    Triple(1, 0, 0), Triple(1, -1, 0), Triple(-1, 1, 0)
                )
            }

            Rail.Shape.ASCENDING_NORTH -> {
                listOf(
                    Triple(0, 0, 1), Triple(0, -1, 1), Triple(0, 1, -1)
                )
            }

            Rail.Shape.ASCENDING_SOUTH -> {
                listOf(
                    Triple(0, 0, -1), Triple(0, -1, -1), Triple(0, 1, 1)
                )
            }

            Rail.Shape.SOUTH_EAST -> {
                listOf(
                    Triple(0, -1, 1), Triple(0, 0, 1), Triple(1, 0, 0), Triple(1, -1, 0)
                )
            }

            Rail.Shape.SOUTH_WEST -> {
                listOf(
                    Triple(0, -1, 1), Triple(0, 0, 1), Triple(-1, 0, 0), Triple(-1, -1, 0)
                )
            }

            Rail.Shape.NORTH_EAST -> {
                listOf(
                    Triple(0, -1, -1), Triple(0, 0, -1), Triple(1, 0, 0), Triple(1, -1, 0)
                )
            }

            Rail.Shape.NORTH_WEST -> {
                listOf(
                    Triple(0, -1, -1), Triple(0, 0, -1), Triple(-1, 0, 0), Triple(-1, -1, 0)
                )
            }
        }
    }

    suspend fun railFinishDetect(
        first: Point3D, directionPoint: Point3D
    ): Either<RailDetectError, Point3D> = withContext(Dispatchers.minecraft) {
        var previousPoint = first
        var currentPoint = directionPoint
        var count = 0
        val line = Line3D(first, directionPoint)

        while (count < 10000) {
            val diff = previousPoint.getDiff(currentPoint)
            val list = getRailAvailableDirection(currentPoint).toMutableList()
            list.removeIf {
                it.first == -diff.first.toInt() && it.second == -diff.second.toInt() && it.third == -diff.third.toInt()
            }
            val nextPoint = list.map { (x, y, z) ->
                Point3D(currentPoint.x + x, currentPoint.y + y, currentPoint.z + z)
            }
            val rails = nextPoint.filter { it.toLocation(Bukkit.getWorld("world")!!).block.blockData is Rail }
            if (rails.isEmpty()) {
                return@withContext currentPoint.right()
            }
            if (rails.size > 1) {
                return@withContext RailDetectError.MULTIPLE_RAIL.left()
            }
            previousPoint = currentPoint
            currentPoint = rails.first()

            if (count % 2 == 0) {
                line.addPoint(currentPoint)
            }
            count++
        }
        return@withContext RailDetectError.ATTACHED_TO_LIMIT.left()
    }

    fun Location.toPoint3D() = Point3D(x, y, z)
    private fun Triple<Int, Int, Int>.toPoint3D() = Point3D(first.toDouble(), second.toDouble(), third.toDouble())
}