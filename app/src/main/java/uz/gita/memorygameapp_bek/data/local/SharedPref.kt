package uz.gita.memorygameapp_bek.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SharedPref private constructor() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var sharedPref: SharedPref

        private lateinit var pref: SharedPreferences
        private lateinit var editor: Editor

        private const val SHARED_PREF = "shared_pref"
        private const val MUSIC = "music"

        fun init(context: Context){
            if (!(::sharedPref.isInitialized)) {
                sharedPref = SharedPref()
            }

            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
            editor = pref.edit()
        }

        fun getInstance() = sharedPref
    }

    var isMusic: Boolean
        set(value) = editor.putBoolean(MUSIC, value).apply()
        get() = pref.getBoolean(MUSIC, true)
}