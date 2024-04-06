/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway

import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

class Point3D(val x: Double, val y: Double, val z: Double) {
    fun distanceTo(point: Point3D): Double {
        return sqrt((point.x - x).pow(2.0) + (point.y - y).pow(2.0) + (point.z - z).pow(2.0))
    }

    fun getInclination(point: Point3D): Pair<Double, Double> {
        val x = point.x - this.x
        val y = point.y - this.y
        val z = point.z - this.z
        val r = sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))
        return Pair(acos(x / r), acos(y / r))
    }

    override fun toString(): String {
        return "Point3D(x=$x, y=$y, z=$z)"
    }

    fun getPointsBetween(end: Point3D, step: Double): List<Point3D> {
        val distance = distanceTo(end)
        val points = mutableListOf<Point3D>()
        val start = this
        var current = start
        val x = (end.x - x) / distance * step
        val y = (end.y - y) / distance * step
        val z = (end.z - z) / distance * step

        if (start.x >= end.x) {
            while (current.x >= end.x) {
                points.add(current)
                current = Point3D(current.x - x, current.y - y, current.z - z)
            }
        } else {
            while (current.x <= end.x) {
                points.add(current)
                current = Point3D(current.x + x, current.y + y, current.z + z)
            }
        }

        return points
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Point3D

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

}