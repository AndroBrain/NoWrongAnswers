package com.androbrain.data.game

import androidx.datastore.core.Serializer
import androidx.versionedparcelable.VersionedParcel
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.json.Json

object GameSerializer : Serializer<Games> {
    override val defaultValue: Games
        get() = Games()

    override suspend fun readFrom(input: InputStream): Games {
        return try {
            Json.decodeFromString(
                deserializer = Games.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: VersionedParcel.ParcelException) {
            defaultValue
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: Games, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Games.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}
