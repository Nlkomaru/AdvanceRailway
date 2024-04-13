/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.file

import dev.nikomaru.advancerailway.file.loader.ConfigDataLoader
import dev.nikomaru.advancerailway.file.loader.RailwayDataLoader
import dev.nikomaru.advancerailway.file.loader.StationDataLoader

object FileLoader {
    suspend fun load() {
        ConfigDataLoader().load()
        StationDataLoader().load()
        RailwayDataLoader().load()
    }

    fun export() {
        TODO("Not yet implemented")
    }
}