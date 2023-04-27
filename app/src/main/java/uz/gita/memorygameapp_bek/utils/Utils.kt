package uz.gita.memorygameapp_bek.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import uz.gita.memorygameapp_bek.R
import uz.gita.memorygameapp_bek.data.CardData

@SuppressLint("Recycle")
fun ImageView.openImage(onStartAction: (Animator) -> Unit, onEndAction: (Animator) -> Unit) {
    val openAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 89f)
    val closeAnimator: ValueAnimator = ValueAnimator.ofFloat(89f, 0f)

    openAnimator.addUpdateListener {
        val value = it.animatedValue as Float

        rotationY = value

        if(value == 89f) {
            val resource = (tag as CardData).imgRes
            setImageResource(resource)
        }
    }

    closeAnimator.addUpdateListener {
        val value = it.animatedValue as Float

        rotationY = value
    }
    openAnimator.duration = 300

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(openAnimator, closeAnimator)
    animatorSet.doOnStart(onStartAction)
    animatorSet.doOnEnd(onEndAction)
    animatorSet.start()
}

@SuppressLint("Recycle")
fun ImageView.closeImage(onStartAction: (Animator) -> Unit, onEndAction: (Animator) -> Unit) {
    val openAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, -89f)
    val closeAnimator: ValueAnimator = ValueAnimator.ofFloat(-89f, 0f)

    openAnimator.addUpdateListener {
        val value = it.animatedValue as Float

        rotationY = value

        if(value == -89f) {
            setImageResource(R.drawable.image_back)
        }
    }

    closeAnimator.addUpdateListener {
        val value = it.animatedValue as Float

        rotationY = value
    }
    openAnimator.duration = 300

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(openAnimator, closeAnimator)
    animatorSet.doOnStart(onStartAction)
    animatorSet.doOnEnd(onEndAction)
    animatorSet.start()
}
