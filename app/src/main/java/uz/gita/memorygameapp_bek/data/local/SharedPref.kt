package uz.gita.memorygameapp_bek.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SharedPref private constructor(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var sharedPref: SharedPref

        private lateinit var pref: SharedPreferences
        private lateinit var editor: Editor

        private const val SHARED_PREF = "shared_pref"
        private const val MUSIC = "music"

        fun getInstance(context: Context): SharedPref {
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
            editor = pref.edit()


            if (!(::sharedPref.isInitialized)) {
                sharedPref = SharedPref(context)
            }
            return sharedPref
        }
    }

    var isMusic: Boolean
        set(value) = editor.putBoolean(MUSIC, value).apply()
        get() = pref.getBoolean(MUSIC, true)
}