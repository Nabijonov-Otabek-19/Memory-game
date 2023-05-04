package uz.gita.memorygameapp_bek.utils

import android.content.Context
import android.media.MediaPlayer
import uz.gita.memorygameapp_bek.R

class SoundController private constructor() {

    companion object {
        private var musicPlayer: MediaPlayer? = null
        private lateinit var soundController: SoundController

        fun init(context: Context) {
            if (musicPlayer == null) {
                musicPlayer = MediaPlayer.create(context, R.raw.music)
            }

            if (!(::soundController.isInitialized)) {
                soundController = SoundController()
            }
        }

        fun getInstance() = soundController
    }

    fun playMusic() {
        musicPlayer!!.isLooping = true
        musicPlayer!!.start()
    }

    fun stopMusic() {
        musicPlayer!!.stop()
        musicPlayer!!.release()
    }
}