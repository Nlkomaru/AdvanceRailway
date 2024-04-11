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
import dev.nikomaru.advancerailway.AdvanceRailway
import dev.nikomaru.advancerailway.error.StationSearchError
import dev.nikomaru.advancerailway.file.data.StationData
import dev.nikomaru.advancerailway.file.value.StationId
import dev.nikomaru.advancerailway.utils.Utils.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.Location
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object StationUtils: KoinComponent {
    val plugin: AdvanceRailway by inject()

    suspend fun nearStation(location: Location): Either<StationSearchError, StationId> = withContext(Dispatchers.IO) {
        val folder = plugin.dataFolder.resolve("data").resolve("station")
        if (!folder.exists()) {
            folder.mkdirs()
            return@withContext Either.Left(StationSearchError.NOT_FOUND_STATION)
        }
        val files = folder.listFiles() ?: return@withContext Either.Left(StationSearchError.NOT_FOUND_STATION)
        val station =
            files.map { StationId(it.nameWithoutExtension) }.map { getStationData(it) }.filter { it.isRight() }
                .map { it.getOrNull()!! }
        val world = location.world //TODO 距離の計算を変更する ネザーの場合は8倍にする
        station.sortedBy { it.point.toLocation(location.world).distance(location) }
        return@withContext Either.Right(station.first().id)
    }

    suspend fun getStationData(stationId: StationId): Either<StationSearchError, StationData> =
        withContext(Dispatchers.IO) {
            val folder = plugin.dataFolder.resolve("data").resolve("station")
            if (!folder.exists()) {
                folder.mkdirs()
                return@withContext Either.Left(StationSearchError.NOT_FOUND_STATION)
            }
            val file = folder.resolve("${stationId.value}.json")
            if (!file.exists()) {
                return@withContext Either.Left(StationSearchError.NOT_FOUND_STATION)
            }
            return@withContext Either.Right(json.decodeFromString(file.readText()))
        }
}