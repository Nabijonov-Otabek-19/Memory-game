package uz.gita.memorygameapp_bek.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygameapp_bek.R
import uz.gita.memorygameapp_bek.data.CardData
import uz.gita.memorygameapp_bek.data.LevelEnum
import uz.gita.memorygameapp_bek.databinding.ScreenGameBinding
import uz.gita.memorygameapp_bek.repository.AppRepository

class GameScreen : Fragment(R.layout.screen_game) {

    private val binding: ScreenGameBinding by viewBinding(ScreenGameBinding::bind)
    private var defLevel = LevelEnum.EASY
    private val args by navArgs<GameScreenArgs>()
    private val repository = AppRepository()
    private var _height = 0
    private var _width = 0
    private val images = ArrayList<ImageView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        defLevel = args.level

        binding.space.post {
            _height = binding.container.height / defLevel.verCount
            _width = binding.container.width / defLevel.horCount
            val count = defLevel.horCount * defLevel.verCount
            val ls = repository.getData(count)
            describeCardData(ls)
        }
        //  viewTreeObserver
    }

    private fun describeCardData(ls: List<CardData>) {
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
                image.tag = ls[i * defLevel.verCount + j]
                //  image.setImageResource(ls[i* defLevel.verCount + j].imgRes)
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
                val bool = (it as ImageView).drawable.constantState ==
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.image_back
                        )?.constantState

                if (bool) open(it)
                else close(it)
            }
        }
    }

    private fun open(imageView: ImageView) {
        imageView.animate()
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
                        //Toast.makeText(requireContext(), data.id.toString(), Toast.LENGTH_SHORT).show()
                    }.start()
            }.start()
    }

    private fun close(imageView: ImageView) {
        imageView.animate()
            .setDuration(500)
            .rotationY(91f)
            .withEndAction {
                val data = imageView.tag as CardData
                imageView.setImageResource(R.drawable.image_back)
                imageView.rotationY = 89f
                imageView.animate()
                    .setDuration(500)
                    .rotationY(0f)
                    .withEndAction {
                        //Toast.makeText(requireContext(), data.id.toString(), Toast.LENGTH_SHORT).show()
                    }.start()
            }.start()
    }
}