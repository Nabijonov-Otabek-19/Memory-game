package uz.gita.memorygameapp_bek.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygameapp_bek.data.LevelEnum
import uz.gita.memorygameapp_bek.R
import uz.gita.memorygameapp_bek.databinding.ScreenLevelBinding

class LevelScreen : Fragment(R.layout.screen_level) {
    private val binding : ScreenLevelBinding by viewBinding(ScreenLevelBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.easy.setOnClickListener {
            openGameScreen(LevelEnum.EASY)
        }
        binding.medium.setOnClickListener {
            openGameScreen(LevelEnum.MEDIUM)
        }
        binding.hard.setOnClickListener {
            openGameScreen(LevelEnum.HARD)
        }
    }

    private fun openGameScreen(level: LevelEnum) {
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(level))
    }
}
