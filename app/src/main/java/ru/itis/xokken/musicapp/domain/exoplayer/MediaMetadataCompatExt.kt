package ru.itis.xokken.musicapp.domain.exoplayer

import android.support.v4.media.MediaMetadataCompat
import ru.itis.xokken.musicapp.data.model.Song

fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {
        Song(
            it.mediaId ?: "",
            it.title.toString(),
            it.subtitle.toString(),
            it.mediaUri.toString(),
            it.iconUri.toString()
        )
    }
}