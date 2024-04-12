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
import dev.nikomaru.advancerailway.error.DataSearchError
import dev.nikomaru.advancerailway.file.data.GroupData
import dev.nikomaru.advancerailway.file.value.GroupId
import dev.nikomaru.advancerailway.utils.Utils.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object GroupUtils: KoinComponent {
    val plugin: AdvanceRailway by inject()
    suspend fun getGroupData(groupId: GroupId): Either<DataSearchError, GroupData> = withContext(Dispatchers.IO) {
        val folder = StationUtils.plugin.dataFolder.resolve("data").resolve("groups")
        if (!folder.exists()) {
            folder.mkdirs()
            return@withContext Either.Left(DataSearchError.NOT_FOUND)
        }
        val file = folder.resolve("${groupId.value}.json")
        if (!file.exists()) {
            return@withContext Either.Left(DataSearchError.NOT_FOUND)
        }
        return@withContext Either.Right(json.decodeFromString(file.readText()))
    }
}