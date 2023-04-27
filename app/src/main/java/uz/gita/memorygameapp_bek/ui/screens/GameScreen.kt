package uz.gita.memorygameapp_bek.ui.screens

import android.animation.Animator
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
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygameapp_bek.R
import uz.gita.memorygameapp_bek.data.CardData
import uz.gita.memorygameapp_bek.data.LevelEnum
import uz.gita.memorygameapp_bek.databinding.ScreenGameBinding
import uz.gita.memorygameapp_bek.repository.AppRepository
import uz.gita.memorygameapp_bek.utils.closeImage
import uz.gita.memorygameapp_bek.utils.openImage

class GameScreen : Fragment(R.layout.screen_game) {

    private val binding: ScreenGameBinding by viewBinding(ScreenGameBinding::bind)
    private var defLevel = LevelEnum.EASY
    private val args by navArgs<GameScreenArgs>()
    private val repository = AppRepository.getInstance()
    private var _height = 0
    private var _width = 0
    private val images = ArrayList<ImageView>()

    private var step = 0
    private var attempt = 0
    private var isCompl = 0

    private val pair = ArrayList<ImageView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        defLevel = args.level

        resizeImages()
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
        binding.attempt.text = "0"
        binding.container.removeAllViews()
        images.clear()
        isCompl = 0

        for (i in 0 until defLevel.horCount) {
            for (j in 0 until defLevel.verCount) {
                val image = ImageView(requireContext())
                binding.container.addView(image)
                val lp = image.layoutParams as ConstraintLayout.LayoutParams
                lp.apply {
                    width = _width
                    height = _height
                }
                lp.setMargins(4, 4, 4, 4)
//                image.x = i * _width * 1f
//                image.y = j * _height * 1f
                image.layoutParams = lp
                image.tag = list[i * defLevel.verCount + j]
                //  image.setImageResource(list[i* defLevel.verCount + j].imgRes)
                image.setImageResource(R.drawable.image_back)
                image.scaleType = ImageView.ScaleType.CENTER_CROP
                image.animate()
                    .x(i * _width * 1f)
                    .y(j * _height * 1f)
                    .setDuration(1000)
                    .start()
                images.add(image)
            }
        }
        addClickListener()
    }

    private fun addClickListener() {
        images.forEach { imageView ->
            imageView.setOnClickListener {
                val isSame = (it as ImageView).drawable.constantState ==
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.image_back
                        )?.constantState

                if (isSame) {
                    step++
                    if (step == 1) {
                        pair.add(it)
                        open(it)
                    } else if (step == 2) {
                        open(it)
                        pair.add(it)
                        val image1 = pair[0]
                        val image2 = pair[1]

                        val bool2 = (image1.tag as CardData).id == (image2.tag as CardData).id
                        if (bool2) {
                            binding.attempt.text = (++attempt).toString()
                            isCompl += 2

                            // bir xil image larni ko'rinmas qiladi
                            Handler(Looper.getMainLooper()).postDelayed({
                                image1.visibility = View.INVISIBLE
                                image2.visibility = View.INVISIBLE
                                pair.clear()
                                step = 0
                            }, 1000)

                            // image lar qolmasa dialog chiqadi
                            if (isCompl == images.size) {
                                Handler(Looper.getMainLooper()).postDelayed({
                                    showDialog()
                                }, 500)
                            }

                        } else {
                            // 2ta bosilgan image bir xil bo'masa, yopadi
                            Handler(Looper.getMainLooper()).postDelayed({
                                binding.attempt.text = (++attempt).toString()
                                pair.forEach { img ->
                                    close(img)
                                    pair.remove(img)
                                }
                                step = 0
                            }, 800)
                        }
                    }
                }
            }
        }
    }

    private fun addClickListener3() {
        images.forEach { imageView ->
            imageView.setOnClickListener {
                if (pair.contains(imageView) || pair.size == 2) {
                    return@setOnClickListener
                }
                open(it as ImageView)
                pair.add(it)

                imageView.openImage({
                    binding.container.isClickable = false
                    pair.add(imageView)
                },
                    {
                        binding.container.isClickable = true
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (pair.size == 2) {
                                val data1 = pair[0].tag as CardData
                                val data2 = pair[1].tag as CardData
                                if (data1 != data2) {
                                    pair.forEach {
                                        it.closeImage({ animator ->
                                            binding.container.isClickable = false
                                        }, { animator ->
                                            pair.remove(it)
                                            binding.container.isClickable = true
                                        })
                                    }
                                } else {
                                    pair.forEach {
                                        it.animate().alpha(0.5f).start()
                                        it.isClickable = false
                                    }
                                    pair.clear()
                                }
                            }
                        }, 500)
                    }
                )
            }
        }
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())

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

        val btnHome: AppCompatButton = dialog.findViewById(R.id.btnHome)
        val btnRestart: AppCompatButton = dialog.findViewById(R.id.btnRestart)

        btnHome.setOnClickListener {
            findNavController().popBackStack()
            dialog.dismiss()
        }

        btnRestart.setOnClickListener {
            resizeImages()
            dialog.dismiss()
        }
        dialog.create()
        dialog.show()
    }

    private fun open(imageView: ImageView) {
        imageView.animate()
            .withStartAction {
                binding.container.isClickable = false
            }
            .setDuration(500)
            .rotationY(89f)
            .withEndAction {
                val data = imageView.tag as CardData
                imageView.setImageResource(data.imgRes)
                imageView.rotationY = -89f
                imageView.animate()
                    .setDuration(500)
                    .rotationY(0f)
                    .withEndAction {
                        binding.container.isClickable = true
                    }
                    .start()
            }.start()
    }

    private fun close(imageView: ImageView) {
        imageView.animate()
            .withStartAction {
                binding.container.isClickable = false
            }
            .setDuration(500)
            .rotationY(91f)
            .withEndAction {
                imageView.setImageResource(R.drawable.image_back)
                imageView.rotationY = 89f
                imageView.animate()
                    .setDuration(500)
                    .rotationY(0f)
                    .withEndAction {
                        binding.container.isClickable = true
                    }
                    .start()
            }.start()
    }
}