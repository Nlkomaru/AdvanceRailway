/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.file

import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.file.data.RailwayData
import dev.nikomaru.advancerailway.file.data.StationData
import dev.nikomaru.advancerailway.utils.Utils.json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.jpenilla.squaremap.api.Key
import xyz.jpenilla.squaremap.api.Point
import xyz.jpenilla.squaremap.api.SimpleLayerProvider
import xyz.jpenilla.squaremap.api.marker.Marker
import xyz.jpenilla.squaremap.api.marker.MarkerOptions
import java.util.*


class StationDataLoader: KoinComponent {
    private val plugin: AdvanceRailway by inject()
    private val provider: SimpleLayerProvider by inject()
    private val stationDataFolder = plugin.dataFolder.resolve("data").resolve("station")
    private val railwayDataFolder = plugin.dataFolder.resolve("data").resolve("railway")
    private val joinedCount = hashMapOf<UUID, Int>()

    fun load() {
        if (!stationDataFolder.exists()) {
            stationDataFolder.mkdirs()
        }
        if (!railwayDataFolder.exists()) {
            railwayDataFolder.mkdirs()
        }
        railwayDataFolder.listFiles()?.forEach { file ->
            val data = json.decodeFromString<RailwayData>(file.readText())
            val station = data.stations
            joinedCount[station.first] = joinedCount.getOrDefault(station.first, 0) + 1
            joinedCount[station.second] = joinedCount.getOrDefault(station.second, 0) + 1
        }

        stationDataFolder.listFiles()?.forEach { file ->
            val stationData = json.decodeFromString<StationData>(file.readText())
            val key = Key.of(stationData.uuid.toString())
            val colorOption =
                MarkerOptions.builder().fillColor(stationData.color.brighter()).strokeColor(stationData.color).build()
            val marker = Marker.circle(
                Point.of(stationData.point.x, stationData.point.y), 5.0 * joinedCount[stationData.uuid]!!
            ).markerOptions(colorOption)

            provider.addMarker(key, marker)
        }
    }
}