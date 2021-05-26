package ru.itis.xokken.musicapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import ru.itis.xokken.musicapp.data.model.Song
import ru.itis.xokken.musicapp.domain.other.Constants.SONG_COLLECTION
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MusicDatabase @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val songCollection = firestore.collection(SONG_COLLECTION)

    suspend fun getAllSongs(): List<Song> {
        return try {
            songCollection.get().await().toObjects(Song::class.java)
        } catch(e: Exception) {
            emptyList()
        }
    }
}