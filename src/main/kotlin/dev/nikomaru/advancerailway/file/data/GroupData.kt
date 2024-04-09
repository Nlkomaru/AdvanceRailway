/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package dev.nikomaru.advancerailway.file.data

import dev.nikomaru.advancerailway.file.utils.ColorSerializer
import kotlinx.serialization.Serializable
import java.awt.Color

data class GroupData(
    val groupId: String,
    val groupName: String,
    val railwayColor: @Serializable(with = ColorSerializer::class) Color,
)