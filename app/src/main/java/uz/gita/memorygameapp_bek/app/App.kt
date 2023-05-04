package uz.gita.memorygameapp_bek.app

import android.app.Application
import uz.gita.memorygameapp_bek.data.local.SharedPref
import uz.gita.memorygameapp_bek.utils.SoundController

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        SoundController.init(this)
        SharedPref.init(this)
    }
}