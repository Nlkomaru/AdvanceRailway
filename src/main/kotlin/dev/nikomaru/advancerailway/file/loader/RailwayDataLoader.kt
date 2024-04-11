/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.file.loader

import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.file.data.RailwayData
import dev.nikomaru.advancerailway.utils.Utils.json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.jpenilla.squaremap.api.Key
import xyz.jpenilla.squaremap.api.Point
import xyz.jpenilla.squaremap.api.SimpleLayerProvider
import xyz.jpenilla.squaremap.api.marker.Marker

class RailwayDataLoader: KoinComponent {
    private val plugin: AdvanceRailway by inject()
    private val provider: SimpleLayerProvider by inject()
    private val dataFolder = plugin.dataFolder.resolve("data").resolve("railway")

    fun load() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        dataFolder.listFiles()?.forEach { file ->
            val data = json.decodeFromString<RailwayData>(file.readText())
            val key = Key.of(data.id.value)
            val marker = Marker.multiPolyline(data.line.points.map { Point.of(it.x, it.y) })
            provider.addMarker(key, marker)
        }
    }
}