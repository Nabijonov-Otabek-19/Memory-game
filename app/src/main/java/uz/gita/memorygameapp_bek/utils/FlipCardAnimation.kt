package uz.gita.memorygameapp_bek.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import uz.gita.memorygameapp_bek.R

@SuppressLint("Recycle")
fun ImageView.flipCard(
    newImageResource: Int,
    flipDuration: Long = 1000,
    onReverse: Boolean = false
) {

    val scale = context.resources.displayMetrics.density
    val cameraDist = 8000 * scale
    this.cameraDistance = cameraDist


    val frontAnim = ObjectAnimator.ofFloat(
        this,
        "rotationY",
        0f, if (!onReverse) 90f else -90f
    ).apply {
        duration = flipDuration / 2
        interpolator = AccelerateInterpolator()
    }

    val backAnim = ObjectAnimator.ofFloat(
        this,
        "rotationY",
        if (!onReverse) -90f else 90f, 0f
    ).apply {
        duration = flipDuration / 2
        interpolator = DecelerateInterpolator()
    }

    val flipAnimSet = AnimatorSet().apply {
        playSequentially(frontAnim, backAnim)
    }

    frontAnim.doOnEnd {
        val resourceId = if (!onReverse) newImageResource else R.drawable.back1
        this.setImageResource(resourceId)
    }

    flipAnimSet.startDelay = 100
    flipAnimSet.start()
}

@SuppressLint("Recycle")
fun closeCardsTogether(card1: ImageView, card2: ImageView, flipDuration: Long = 1000) {
    val scale = card1.context.resources.displayMetrics.density
    val cameraDist = 8000 * scale
    card1.cameraDistance = cameraDist
    card2.cameraDistance = cameraDist

    val frontAnimator1 = ObjectAnimator.ofFloat(
        card1,
        "rotationY",
        0f, -90f
    ).apply {
        duration = flipDuration / 2
        interpolator = AccelerateInterpolator()
    }

    val frontAnimator2 = ObjectAnimator.ofFloat(
        card2,
        "rotationY",
        0f, -90f
    ).apply {
        duration = flipDuration / 2
        interpolator = AccelerateInterpolator()
    }

    val backAnimator1 = ObjectAnimator.ofFloat(
        card1,
        "rotationY",
        90f, 0f
    ).apply {
        duration = flipDuration / 2
        interpolator = DecelerateInterpolator()
    }

    val backAnimator2 = ObjectAnimator.ofFloat(
        card2,
        "rotationY",
        90f, 0f
    ).apply {
        duration = flipDuration / 2
        interpolator = DecelerateInterpolator()
    }

    val flipAnimSet1 = AnimatorSet().apply {
        playSequentially(frontAnimator1, backAnimator1)
        startDelay = 100
    }

    val flipAnimSet2 = AnimatorSet().apply {
        playSequentially(frontAnimator2, backAnimator2)
        startDelay = 100
    }

    frontAnimator1.doOnEnd {
        val resourceId = R.drawable.back1
        card1.setImageResource(resourceId)
        card2.setImageResource(resourceId)
    }

    frontAnimator2.doOnEnd {
        val resourceId = R.drawable.back1
        card1.setImageResource(resourceId)
        card2.setImageResource(resourceId)
    }

    val parentAnimator = AnimatorSet().apply {
        playTogether(flipAnimSet1, flipAnimSet2)
    }

    parentAnimator.startDelay = 1000
    parentAnimator.start()
}

