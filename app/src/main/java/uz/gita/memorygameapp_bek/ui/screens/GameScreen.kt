package uz.gita.memorygameapp_bek.ui.screens

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygameapp_bek.R
import uz.gita.memorygameapp_bek.data.common.CardData
import uz.gita.memorygameapp_bek.data.common.LevelEnum
import uz.gita.memorygameapp_bek.databinding.ScreenGameBinding
import uz.gita.memorygameapp_bek.repository.AppRepository
import uz.gita.memorygameapp_bek.utils.closeCardsTogether
import uz.gita.memorygameapp_bek.utils.flipCard

class GameScreen : Fragment(R.layout.screen_game) {

    private val binding: ScreenGameBinding by viewBinding(ScreenGameBinding::bind)
    private var defLevel = LevelEnum.EASY
    private val args by navArgs<GameScreenArgs>()
    private val repository = AppRepository.getInstance()
    private var _height = 0
    private var _width = 0
    private val images = ArrayList<ImageView>()

    private var attempt = 0
    private var level = 1
    private var totalLevel = 5
    private var isCompl = 0

    private val pair = ArrayList<ImageView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        defLevel = args.level

        level = 1
        resizeImages()

        binding.apply {
            reload.setOnClickListener {
                resizeImages()
            }
        }
    }

    private fun resizeImages() {
        binding.space.post {
            _height = binding.container.height / defLevel.verCount
            _width = binding.container.width / defLevel.horCount
            val count = defLevel.horCount * defLevel.verCount
            val list = repository.getData(count)

            describeCardData(list)
        }
    }

    private fun describeCardData(list: List<CardData>) {
        binding.txtLevel.text = "Level\n$level/$totalLevel"
        binding.attempt.text = "Attemp\n0"
        binding.container.removeAllViews()
        images.clear()
        isCompl = 0
        attempt = 0

        for (i in 0 until defLevel.horCount) {
            for (j in 0 until defLevel.verCount) {
                val image = ImageView(requireContext())
                binding.container.addView(image)
                val lp = image.layoutParams as ConstraintLayout.LayoutParams
                lp.apply {
                    width = _width
                    height = _height
                }
                lp.setMargins(2, 2, 2, 2)
//                image.x = i * _width * 1f
//                image.y = j * _height * 1f
                image.layoutParams = lp
                image.tag = list[i * defLevel.verCount + j]
                //  image.setImageResource(list[i* defLevel.verCount + j].imgRes)
                image.setImageResource(R.drawable.back1)
                image.animate()
                    .x(i * _width * 1f)
                    .y(j * _height * 1f)
                    .setDuration(500)
                    .start()
                images.add(image)
            }
        }
        openAndCloseImages()
        addClickListener()
    }

    private fun openAndCloseImages() {
        Handler(Looper.getMainLooper()).postDelayed({
            images.forEach {
                it.isEnabled = false
                val data = it.tag as CardData
                it.flipCard(data.imgRes)
            }
        }, 500)

        Handler(Looper.getMainLooper()).postDelayed({
            images.forEach {
                it.flipCard(R.drawable.back1)
                it.isEnabled = true
            }
        }, 1800)
    }

    private fun addClickListener() {
        images.forEach { imageView ->
            imageView.setOnClickListener {

                if (pair.contains(imageView) || pair.size == 2) {
                    return@setOnClickListener
                }

                val data = imageView.tag as CardData
                imageView.flipCard(data.imgRes)
                pair.add(imageView)

                checkImages()
            }
        }
    }

    private fun checkImages() {
        if (pair.size == 2) {
            val img1 = pair[0]
            val img2 = pair[1]

            pair.clear()

            if ((img2.tag as CardData) == (img1.tag as CardData)) {
                binding.attempt.text = "Attempt\n${++attempt}"
                isCompl += 2

                img1.animate().alpha(0.5f).start()
                img2.animate().alpha(0.5f).start()
                img1.isClickable = false
                img2.isClickable = false

                if (isCompl == images.size && level == totalLevel) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        showWinAllDialog()
                    }, 1000)
                } else if (isCompl == images.size && level != totalLevel) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        showWinDialog()
                    }, 1000)
                }

            } else {
                binding.attempt.text = "Attempt\n${++attempt}"
                closeCardsTogether(img1, img2)
            }
        }
    }

    private fun showWinDialog() {
        val dialog = Dialog(requireActivity())

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        val window = dialog.window
        window!!.attributes = lp

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_win_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val btnHome: AppCompatTextView = dialog.findViewById(R.id.btnHome)
        val btnNext: AppCompatTextView = dialog.findViewById(R.id.btnNext)

        btnHome.setOnClickListener {
            dialog.dismiss()
            findNavController().popBackStack()
        }

        btnNext.setOnClickListener {
            level++
            resizeImages()
            dialog.dismiss()
        }
        dialog.create()
        dialog.show()
    }

    private fun showWinAllDialog() {
        val dialog = Dialog(requireActivity())

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        val window = dialog.window
        window!!.attributes = lp

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_win_all_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val btnHome: AppCompatTextView = dialog.findViewById(R.id.btnHome)

        btnHome.setOnClickListener {
            dialog.dismiss()
            findNavController().popBackStack()
        }
        dialog.create()
        dialog.show()
    }
}