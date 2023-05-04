package uz.gita.memorygameapp_bek.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygameapp_bek.R
import uz.gita.memorygameapp_bek.data.common.LevelEnum
import uz.gita.memorygameapp_bek.data.local.SharedPref
import uz.gita.memorygameapp_bek.databinding.ScreenLevelBinding
import uz.gita.memorygameapp_bek.utils.SoundController

class LevelScreen : Fragment(R.layout.screen_level) {
    private val binding: ScreenLevelBinding by viewBinding(ScreenLevelBinding::bind)
    private val sharedPref by lazy { SharedPref.getInstance() }
    private val soundController by lazy { SoundController.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (sharedPref.isMusic) {
            soundController.playMusic()
            binding.btnMusic.setImageResource(R.drawable.volume)
        } else {
            binding.btnMusic.setImageResource(R.drawable.mute)
        }

        binding.btnMusic.setOnClickListener {
            if (sharedPref.isMusic) {
                sharedPref.isMusic = false
                binding.btnMusic.setImageResource(R.drawable.mute)
                soundController.stopMusic()

            } else {
                sharedPref.isMusic = true
                binding.btnMusic.setImageResource(R.drawable.volume)
                soundController.playMusic()
            }
        }

        binding.apply {
            easy.setOnClickListener {
                openGameScreen(LevelEnum.EASY)
            }

            medium.setOnClickListener {
                openGameScreen(LevelEnum.MEDIUM)
            }

            hard.setOnClickListener {
                openGameScreen(LevelEnum.HARD)
            }
        }
    }

    private fun openGameScreen(level: LevelEnum) {
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(level))
    }
}
