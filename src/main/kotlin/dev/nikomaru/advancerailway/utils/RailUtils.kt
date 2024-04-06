/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.utils

import org.bukkit.block.data.Rail

object RailUtils {
    fun getRailAvailableDirection(state: Rail.Shape): List<Triple<Int, Int, Int>> {
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
}